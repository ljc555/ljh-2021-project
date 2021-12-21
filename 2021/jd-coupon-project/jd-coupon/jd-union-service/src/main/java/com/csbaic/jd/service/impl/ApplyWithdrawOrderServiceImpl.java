package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.convert.ObjectConvert;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.config.application.ApplicationConstants;
import com.csbaic.jd.config.application.ApplicationProperties;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.*;
import com.csbaic.jd.enums.Role;
import com.csbaic.jd.enums.WithdrawOrder;
import com.csbaic.jd.extend.sms.validate.ValidateArgs;
import com.csbaic.jd.mapper.ApplyWithdrawOrderMapper;
import com.csbaic.jd.service.*;
import com.csbaic.jd.service.sms.SmsBiz;
import com.csbaic.jd.service.sms.SmsService;
import com.github.binarywang.wxpay.bean.entpay.EntPayQueryResult;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * <p>
 * 提现申请单 服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-22
 */
@Slf4j
@Service
public class ApplyWithdrawOrderServiceImpl extends ServiceImpl<ApplyWithdrawOrderMapper, ApplyWithdrawOrderEntity> implements IApplyWithdrawOrderService {

    @Autowired
    private IWalletService walletService;

    @Autowired
    private IApplyWithdrawOrderOperateRecordService operateRecordService;

    @Autowired
    private IUserService userService;


    @Autowired
    private SmsService smsService;

    @Autowired
    private IWithdrawOrderPaymentService paymentService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private IWechatUserService wechatUserService;

    @Autowired
    private IWalletTransactionFlowService transactionFlowService;


    @Autowired
    private ApplicationProperties properties;



    private final static Object APPLY_LOCK = new Object();

    /**
     * 支付提现单锁
     */
    private final static Lock PAYMENT_LOCK = new ReentrantLock();

    @Override
    public WithdrawAccountInfo getWithdrawAccountInfo(Long userId) {
        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST);
        }

        WalletEntity walletEntity  = walletService.getOne(
                Wrappers.<WalletEntity>query()
                .eq(WalletEntity.USER_ID, userId)
        );


        BigDecimal balance = BigDecimal.ZERO;
        if(walletEntity != null){
            balance = walletEntity.getBalance();

            //送去已被冻结的金额
            if(walletEntity.getBalanceFreeze() != null){
                balance = balance.subtract(walletEntity.getBalanceFreeze());
            }

        }

        WithdrawAccountInfo withdrawAccountInfo = new WithdrawAccountInfo();
        withdrawAccountInfo.setBalance(balance);
        withdrawAccountInfo.setWithdrawLimit("每个月27-30号全天可申请提现");

        return withdrawAccountInfo;
    }

    @Transactional
    @Override
    public void updateApplyWithdrawOrder(Long userId, UpdateWithDrawOrder withdrawInfo) {
        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST);
        }

        if(withdrawInfo.getId() == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "申请单Id不能为空");
        }

        if(withdrawInfo.getStatus() == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "更新状态不能为空");
        }


        WithdrawOrder withdrawOrderToUpdate = WithdrawOrder.statusOf(withdrawInfo.getStatus());

        if(withdrawOrderToUpdate == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "更新状态不正解");
        }


        ApplyWithdrawOrderEntity applyWithdrawOrderEntity = getById(withdrawInfo.getId());
        if(applyWithdrawOrderEntity  == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "未找到申请提现单");
        }


        WithdrawOrder withdrawOrder  = WithdrawOrder.statusOf(applyWithdrawOrderEntity.getStatus());

        if(withdrawOrder == WithdrawOrder.APPLYING && (
                            withdrawOrderToUpdate == WithdrawOrder.FINISHED
                        || withdrawOrderToUpdate == WithdrawOrder.PENDING_PAYMENT
                        || withdrawOrderToUpdate == WithdrawOrder.PAID
                )){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "请先审核申请单");
        }

        UserEntity operator = userService.getById(userId);
        if(operator == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "没有找到操作人");
        }



        applyWithdrawOrderEntity.setStatus(withdrawOrderToUpdate.getStatus());

        boolean success = update(applyWithdrawOrderEntity,
                Wrappers.<ApplyWithdrawOrderEntity>query()
                        .eq(ApplyWithdrawOrderEntity.ID, applyWithdrawOrderEntity.getId())
                        .eq(ApplyWithdrawOrderEntity.STATUS, withdrawOrder.getStatus())
        );


        if(!success){
            throw BizRuntimeException.from(ResultCode.ERROR, "更新失败，状态就一致");
        }

        String remark = "";

        switch (withdrawOrderToUpdate){

            case APPLYING:

                break;
            case UNDER_REVIEW:
                remark = operator.getNickName() + " 正在审核中";
                break;
            case APPROVED:
                remark = operator.getNickName() + " 已同意您的提现申请";
                break;
            case PENDING_PAYMENT:
                remark = "待收款";
                break;
            case PAID:
                remark = "已支付到微信零钱";
                break;
            case REJECTED:
                remark = operator.getNickName() + " 已拒绝您的提现请";
                break;
            case CANCEL_ADMIN:
                remark = operator.getNickName() + " 已取消您的提现申请";
                break;
            case FINISHED:
                remark = "提现已完成";
                break;
        }


        ApplyWithdrawOrderOperateRecordEntity operateRecordEntity = new ApplyWithdrawOrderOperateRecordEntity();
        operateRecordEntity.setWithdrawOrderId(applyWithdrawOrderEntity.getId());
        operateRecordEntity.setOperateTime(LocalDateTime.now());
        operateRecordEntity.setOperatorName(operator.getNickName());
        operateRecordEntity.setOperatorId(operator.getId());
        operateRecordEntity.setRemark(remark);
        operateRecordService.save(operateRecordEntity);
    }

    @Override
    public void cancelApplyWithdrawOrder(Long userId, Long oid) {

    }

    @Transactional
    @Override
    public void createApplyWithdrawOrder(Long userId, ApplyWithdrawInfo withdrawInfo) {
        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST);
        }

        if(withdrawInfo.getAmount() == null || withdrawInfo.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "提现金额不正确");
        }

        if(withdrawInfo.getPayeeName() == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "收款人姓名不正确");
        }

        if(withdrawInfo.getPayeeCardId() == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "收款人身份证不正确");
        }

        if(withdrawInfo.getWechatId() == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "收款人微信号不正确");
        }

        UserEntity applyUser = userService.getById(userId);
        if(applyUser == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "用户不存在");
        }



        ValidateArgs validateArgs = new ValidateArgs(null, applyUser.getPhone(), withdrawInfo.getCode(), SmsBiz.APPLY_WITHDRAW.name());
        smsService.validate(validateArgs);

        synchronized (APPLY_LOCK){
            saveWithdrawOrder(applyUser, withdrawInfo);
        }

    }

    @Transactional
    protected void saveWithdrawOrder(UserEntity applier,    ApplyWithdrawInfo withdrawInfo){

        int orderCount = count(
                Wrappers.<ApplyWithdrawOrderEntity>query()
                        .eq(ApplyWithdrawOrderEntity.APPLIER_ID, applier.getId())
                        .ne(ApplyWithdrawOrderEntity.STATUS, WithdrawOrder.FINISHED.getStatus())
                        .ne(ApplyWithdrawOrderEntity.STATUS, WithdrawOrder.CANCEL_ADMIN.getStatus())
                        .ne(ApplyWithdrawOrderEntity.STATUS, WithdrawOrder.CANCEL_APPLIER.getStatus())
        );


        if(orderCount > 0){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "已经有一个未完成的提现申请，请不要重复提交");
        }

        BigDecimal amount = withdrawInfo.getAmount();
        if(amount.compareTo(BigDecimal.ONE) < 0){
            throw BizRuntimeException.from(ResultCode.ERROR, "最小提现金额为1元");
        }

        amount = amount.setScale(ApplicationConstants.AMOUNT_PRECISION_FOR_FEE, BigDecimal.ROUND_DOWN);

        BigDecimal walletBalance = walletService.getBalanceWithoutFreeze(applier.getId());
        if(walletBalance.compareTo(amount) < 0){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "钱包余额不足");
        }


        ApplyWithdrawOrderEntity applyWithdrawOrderEntity = new ApplyWithdrawOrderEntity();
        applyWithdrawOrderEntity.setApplierId(applier.getId());
        applyWithdrawOrderEntity.setAmount(amount);
        applyWithdrawOrderEntity.setPayeeName(withdrawInfo.getPayeeName());
        applyWithdrawOrderEntity.setWechatId(withdrawInfo.getWechatId());
        applyWithdrawOrderEntity.setPayeeCardId(withdrawInfo.getPayeeCardId());
        applyWithdrawOrderEntity.setStatus(WithdrawOrder.APPLYING.getStatus());
        save(applyWithdrawOrderEntity);

        ApplyWithdrawOrderOperateRecordEntity operateRecordEntity = new ApplyWithdrawOrderOperateRecordEntity();
        operateRecordEntity.setWithdrawOrderId(applyWithdrawOrderEntity.getId());
        operateRecordEntity.setOperateTime(LocalDateTime.now());
        operateRecordEntity.setOperatorName(applier.getNickName());
        operateRecordEntity.setOperatorId(applier.getId());
        operateRecordEntity.setRemark(applier.getNickName() + " 发起了提现申请");
        operateRecordService.save(operateRecordEntity);
    }

    @Override
    public IPage<WithdrawOrderInfo> getWithdrawOrders(Integer pageIndex, Integer pageSize, Integer status) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 20 : pageSize;


        QueryWrapper<ApplyWithdrawOrderEntity> wrappers =  Wrappers.<ApplyWithdrawOrderEntity>query();


        if (status != null) {
            wrappers.eq(ApplyWithdrawOrderEntity.STATUS, status);
        }


        IPage<ApplyWithdrawOrderEntity> applyWithdrawOrderEntityIPage = page(new Page<>(pageIndex, pageSize), wrappers);
        return applyWithdrawOrderEntityIPage.convert(this::convert);
    }

    @Override
    public IPage<WithdrawOrderInfo> getWithdrawOrders(Long userId, Integer pageIndex, Integer pageSize) {
        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST);
        }

        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 20 : pageSize;

        IPage<ApplyWithdrawOrderEntity> applyWithdrawOrderEntityIPage = page(new Page<>(pageIndex, pageSize),
                    Wrappers.<ApplyWithdrawOrderEntity>query()
                        .eq(ApplyWithdrawOrderEntity.APPLIER_ID, userId)
                );

        return applyWithdrawOrderEntityIPage.convert(this::convert);
    }


    @Override
    @Transactional
    public void payForWithdraw(Long operatorId, Long withdrawId) {

        if(operatorId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "操作人不能为空");
        }


        if(withdrawId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "提现单编号不能为空");
        }


        UserEntity userEntity = userService.getById(operatorId);
        if(userEntity == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "没有找到操作人");
        }


        if(!Objects.equals(userEntity.getRoleName() , Role.ADMIN.name())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "没有操作权限");
        }


        ApplyWithdrawOrderEntity withdrawOrderEntity = null;
        WithdrawOrder withdrawOrder = null;

        withdrawOrderEntity = getById(withdrawId);
        if(withdrawOrderEntity == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "没有找到提现申请单");
        }

        withdrawOrder = WithdrawOrder.statusOf(withdrawOrderEntity.getStatus());
        if(withdrawOrder == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "提现单状态不正确");
        }


        switch (withdrawOrder){

            case APPLYING:
            case UNDER_REVIEW:
            case APPROVED:
            case REJECTED:
            case CANCEL_ADMIN:
            case CANCEL_APPLIER:
            case FINISHED:
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "当前状态不允许支付：" + withdrawOrder);
            case PAID:
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "已经支付过了，不要重复支付");
            case PENDING_PAYMENT:
                break;
        }


        PAYMENT_LOCK.lock();

        try {




            UpdateWithDrawOrder updateWithDrawOrder = new UpdateWithDrawOrder();
            updateWithDrawOrder.setId(withdrawOrderEntity.getId());
            updateWithDrawOrder.setStatus(WithdrawOrder.PAID.getStatus());
            updateApplyWithdrawOrder(operatorId, updateWithDrawOrder);


            WithdrawTransactionFlow withdrawTransactionFlow = new WithdrawTransactionFlow();
            withdrawTransactionFlow.setTxId(withdrawOrderEntity.getId());
            withdrawTransactionFlow.setAmount(withdrawOrderEntity.getAmount());
            withdrawTransactionFlow.setUserId(withdrawOrderEntity.getApplierId());
            transactionFlowService.createTransactionFlow(withdrawTransactionFlow);
            payWithWithdrawOrderByWechat(withdrawOrderEntity);
        } finally {
            PAYMENT_LOCK.unlock();
        }

    }


    /**
     * 使用微信支付提现
     * @param applyWithdrawOrderEntity
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void payWithWithdrawOrderByWechat(ApplyWithdrawOrderEntity applyWithdrawOrderEntity){


        PAYMENT_LOCK.lock();
        WithdrawOrderPaymentEntity paymentEntity = null;

        try{
            paymentEntity = paymentService.getOne(
                    Wrappers.<WithdrawOrderPaymentEntity>query()
                            .eq(WithdrawOrderPaymentEntity.PARTNER_TRADE_NO, applyWithdrawOrderEntity.getId())
            );

            if(paymentEntity != null){
                log.warn("提现单支付已存在，可能已经支付过了");

                log.debug("payment result {}", paymentEntity);
                if(Objects.equals("SUCCESS", paymentEntity.getResultCode())){
                    throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "提现申请单已经支付过了");
                }

                //支付订单已经存在，但是没有支付成功，查询下支付状态
                EntPayQueryResult entPayQueryResult = wxPayService.getEntPayService().queryEntPay(paymentEntity.getPartnerTradeNo());
                log.debug("payment result {}", entPayQueryResult);

                //检查是否已经支付过
                if(Objects.equals(paymentEntity.getResultCode(), "SUCCESS")){
                    log.debug("支付已完成：" + paymentEntity.getErrCode());
                    paymentEntity.setErrCode(entPayQueryResult.getErrCode());
                    return;

                }else if( Objects.equals(paymentEntity.getResultCode(), "PROCESSING")){
                    log.debug("支付正在处理中：" + paymentEntity.getErrCode());
                    paymentEntity.setErrCode(entPayQueryResult.getErrCode());
                    throw BizRuntimeException.from(ResultCode.ERROR, "支付正在处理中");
                }


            }else{
                paymentEntity = new WithdrawOrderPaymentEntity();
            }


            WechatUserEntity wechatUserEntity = wechatUserService.getOne(
                    Wrappers.<WechatUserEntity>query()
                    .eq(WechatUserEntity.USER_ID, applyWithdrawOrderEntity.getApplierId())

            );

            if(wechatUserEntity == null || Strings.isNullOrEmpty(wechatUserEntity.getOpenId())){
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "没有找到收款人");
            }


            EntPayRequest entPayRequest = new EntPayRequest();
            entPayRequest.setAmount(applyWithdrawOrderEntity.getAmount().multiply(new BigDecimal("100")).intValue());
            entPayRequest.setDescription("提现申请支付");
            entPayRequest.setPartnerTradeNo(String.valueOf(applyWithdrawOrderEntity.getId()));
            entPayRequest.setReUserName(applyWithdrawOrderEntity.getPayeeName());
            entPayRequest.setCheckName("FORCE_CHECK");
            entPayRequest.setOpenid(wechatUserEntity.getOpenId());
            entPayRequest.setSpbillCreateIp("101.133.159.195");
            EntPayResult entPayResult = wxPayService.getEntPayService().entPay(entPayRequest);

            paymentEntity.setErrCode(entPayResult.getErrCode());
            paymentEntity.setAmount(entPayRequest.getAmount());
            paymentEntity.setErrCodeDes(entPayResult.getErrCodeDes());
            paymentEntity.setPaymentDesc(entPayRequest.getDescription());
//            paymentEntity.setNonceStr(entPayResult.getNonceStr());
            paymentEntity.setPartnerTradeNo(entPayResult.getPartnerTradeNo());
            paymentEntity.setPaymentNo(entPayResult.getPaymentNo());
            paymentEntity.setPaymentTime(entPayResult.getPaymentTime());
            paymentEntity.setResultCode(entPayResult.getResultCode());
            paymentService.saveOrUpdate(paymentEntity);

            if(!Objects.equals(entPayResult.getReturnCode(), "SUCCESS")
                || !Objects.equals(entPayResult.getResultCode(), "SUCCESS") ){

                throw BizRuntimeException.from(ResultCode.ERROR, entPayResult.getErrCodeDes());
            }

        } catch (WxPayException e) {
            log.error("", e);
            throw BizRuntimeException.from(ResultCode.ERROR, e.getMessage());
        } finally {
            PAYMENT_LOCK.unlock();
        }

    }


    public   WithdrawOrderInfo convert(ApplyWithdrawOrderEntity applyWithdrawOrderEntity){
        WithdrawOrderInfo withdrawOrderInfo = new WithdrawOrderInfo();
        withdrawOrderInfo.setId(applyWithdrawOrderEntity.getId());
        withdrawOrderInfo.setAmount(applyWithdrawOrderEntity.getAmount());
        withdrawOrderInfo.setPayeeName(applyWithdrawOrderEntity.getPayeeName());
        withdrawOrderInfo.setStatus(applyWithdrawOrderEntity.getStatus());
        withdrawOrderInfo.setWechatId(applyWithdrawOrderEntity.getWechatId());
        withdrawOrderInfo.setStatusDesc(WithdrawOrder.statusOf(applyWithdrawOrderEntity.getStatus()).getText());
        withdrawOrderInfo.setSubmitTime(applyWithdrawOrderEntity.getCreateTime());


        List<ApplyWithdrawOrderOperateRecordEntity> operations = operateRecordService.list(
                Wrappers.<ApplyWithdrawOrderOperateRecordEntity>query()
                        .eq(ApplyWithdrawOrderOperateRecordEntity.WITHDRAW_ORDER_ID, applyWithdrawOrderEntity.getId())

        );

        ObjectConvert<ApplyWithdrawOrderOperateRecordEntity, WithdrawOperate> converter =
                ObjectConvert
                .spring(WithdrawOperate.class);

        List<WithdrawOperate> operates = operations.stream()
                .map(converter::convert)
                .collect(Collectors.toList());



        operates.sort(Comparator.comparing(WithdrawOperate::getOperateTime));
        Collections.reverse(operates);
        withdrawOrderInfo.setOperations(operates);


        return withdrawOrderInfo;
    }



}
