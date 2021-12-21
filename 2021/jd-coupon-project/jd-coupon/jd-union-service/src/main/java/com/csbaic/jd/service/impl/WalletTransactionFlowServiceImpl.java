package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.SettlementTransactionFlow;
import com.csbaic.jd.dto.WithdrawTransactionFlow;
import com.csbaic.jd.entity.WalletTransactionFlowEntity;
import com.csbaic.jd.enums.TransactionBiz;
import com.csbaic.jd.enums.TransactionType;
import com.csbaic.jd.mapper.WalletTransactionFlowMapper;
import com.csbaic.jd.service.IWalletService;
import com.csbaic.jd.service.IWalletTransactionFlowService;
import com.csbaic.jd.utils.BigDecimalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 交易流水
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-20
 */
@Slf4j
@Service
public class WalletTransactionFlowServiceImpl extends ServiceImpl<WalletTransactionFlowMapper, WalletTransactionFlowEntity> implements IWalletTransactionFlowService {

    @Autowired
    private IWalletService walletService;

    @Override
    @Transactional
    public void createTransactionFlow(SettlementTransactionFlow transactionFlow) {
        log.info("createTransactionFlow: {}", transactionFlow);
        BigDecimal rebateFee = transactionFlow.getRebateFee();
        BigDecimal awareFee = transactionFlow.getAwardFee();
        BigDecimal commissionFee = transactionFlow.getCommissionFee();

        if(rebateFee != null  && BigDecimalUtils.gt(rebateFee, BigDecimal.ZERO)){
            WalletTransactionFlowEntity transactionFlowEntity = new WalletTransactionFlowEntity();
            transactionFlowEntity.setAmount(rebateFee);
            transactionFlowEntity.setOrderId(transactionFlow.getTxId());
            transactionFlowEntity.setUserId(transactionFlow.getUserId());
            transactionFlowEntity.setTransactionTime(LocalDateTime.now());
            transactionFlowEntity.setRemark((transactionFlow.getStart().getMonth().getValue()) + " 月推广返利结算");
            transactionFlowEntity.setTransactionBiz(TransactionBiz.MONTH_BILLING.getCode());
            transactionFlowEntity.setTransactionType(TransactionType.REVENUE.ordinal());
            save(transactionFlowEntity);

            walletService.addBalance(transactionFlow.getUserId(), rebateFee);
        }

        if(commissionFee != null  && BigDecimalUtils.gt(commissionFee, BigDecimal.ZERO)){
            WalletTransactionFlowEntity transactionFlowEntity = new WalletTransactionFlowEntity();
            transactionFlowEntity.setAmount(commissionFee);
            transactionFlowEntity.setOrderId(transactionFlow.getTxId());
            transactionFlowEntity.setUserId(transactionFlow.getUserId());
            transactionFlowEntity.setTransactionTime(LocalDateTime.now());
            transactionFlowEntity.setRemark((transactionFlow.getStart().getMonth().getValue()) + " 月推广佣金结算");
            transactionFlowEntity.setTransactionBiz(TransactionBiz.MONTH_BILLING.getCode());
            transactionFlowEntity.setTransactionType(TransactionType.REVENUE.ordinal());
            save(transactionFlowEntity);

            walletService.addBalance(transactionFlow.getUserId(), commissionFee);
        }


        if(awareFee != null  && BigDecimalUtils.gt(awareFee, BigDecimal.ZERO)){
            WalletTransactionFlowEntity transactionFlowEntity = new WalletTransactionFlowEntity();
            transactionFlowEntity.setAmount(awareFee);
            transactionFlowEntity.setOrderId(transactionFlow.getTxId());
            transactionFlowEntity.setUserId(transactionFlow.getUserId());
            transactionFlowEntity.setTransactionTime(LocalDateTime.now());
            transactionFlowEntity.setRemark((transactionFlow.getStart().getMonth().getValue()) + " 月平台奖励结算");
            transactionFlowEntity.setTransactionBiz(TransactionBiz.MONTH_BILLING.getCode());
            transactionFlowEntity.setTransactionType(TransactionType.REVENUE.ordinal());
            save(transactionFlowEntity);

            walletService.addBalance(transactionFlow.getUserId(), awareFee);
        }

    }

    @Override
    @Transactional
    public void createTransactionFlow(WithdrawTransactionFlow transactionFlow) {
        log.info("createTransactionFlow: {}", transactionFlow);

        if(transactionFlow.getTxId() == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "交易流水号不能为空");
        }

        if(transactionFlow.getAmount() == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "余额不能为空");
        }

        if(BigDecimalUtils.lt(transactionFlow.getAmount(), BigDecimal.ZERO)){
            throw BizRuntimeException.from(ResultCode.ERROR, "交易金额不正确");
        }


        WalletTransactionFlowEntity transactionFlowEntity = getOne(
                Wrappers.<WalletTransactionFlowEntity>query()
                .eq(WalletTransactionFlowEntity.ORDER_ID, transactionFlow.getTxId())
        );

        if(transactionFlowEntity != null ){
            throw BizRuntimeException.from(ResultCode.ERROR, "交易记录已经存在");
        }

        BigDecimal amount = transactionFlow.getAmount();

        transactionFlowEntity = new WalletTransactionFlowEntity();
        transactionFlowEntity.setAmount(amount);
        transactionFlowEntity.setOrderId(transactionFlow.getTxId());
        transactionFlowEntity.setUserId(transactionFlow.getUserId());
        transactionFlowEntity.setTransactionTime(LocalDateTime.now());
        transactionFlowEntity.setRemark("提现");
        transactionFlowEntity.setTransactionBiz(TransactionBiz.WITHDRAW.getCode());
        transactionFlowEntity.setTransactionType(TransactionType.SPENDING.ordinal());
        save(transactionFlowEntity);

        walletService.subBalance(transactionFlow.getUserId(), amount);
    }
}
