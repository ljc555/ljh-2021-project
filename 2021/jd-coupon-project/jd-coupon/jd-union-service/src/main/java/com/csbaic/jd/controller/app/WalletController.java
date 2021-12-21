package com.csbaic.jd.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.service.IApplyWithdrawOrderService;
import com.csbaic.jd.service.ISettlementCalculateResultService;
import com.csbaic.jd.service.settlement.SettlementService;
import com.csbaic.jd.service.sms.SmsBiz;
import com.csbaic.jd.service.sms.SmsService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@Api(value = "钱包", tags = "钱包")
@ResponseResult
public class WalletController {


    @Autowired
    private IApplyWithdrawOrderService withdrawOrderService;


    @Autowired
    private SettlementService settlementService;

    @Autowired
    private ISettlementCalculateResultService settlementCalculateResultService;

    @Autowired
    private SmsService smsService;



    @GetMapping("/settle_once_for_month")
    @ApiOperation("执行一次结算")
    public void settleOnce( @RequestParam("witch") int witchMonth){
        settlementService.settlementMonthly( witchMonth);
    }


    @GetMapping
    @ApiOperation("获取提现账号信息")
    public WithdrawAccountInfo getWithdrawInfo(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId){
        return withdrawOrderService.getWithdrawAccountInfo(userId);
    }


    @PostMapping("/apply_withdraw")
    @ApiOperation("申请提现")
    public void applyWithdraw(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody ApplyWithdrawInfo info){
        withdrawOrderService.createApplyWithdrawOrder(userId,info);
    }


    @PostMapping("/apply_withdraw_cancel/{id}")
    @ApiOperation("取消申请提现")
    public void cancelWithdraw(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @PathVariable("id") Long orderId ){
        withdrawOrderService.cancelApplyWithdrawOrder(userId, orderId);
    }







    @PostMapping("/send_apply_withdraw_code")
    @ApiOperation("发送申请提现验证码")
    public void sendApplyWithdrawCode(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody PhoneNumber phoneNumber){
        smsService.send(phoneNumber.getPhoneExt(), phoneNumber.getPhone(), SmsBiz.APPLY_WITHDRAW);
    }



    @GetMapping("/withdraw_records")
    @ApiOperation("提现记录")
    public IPage<WithdrawOrderInfo> getWithdrawRecords(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
                                                       @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                                       @RequestParam(value = "pageSize", defaultValue = "20") int pageSize ){
        return withdrawOrderService.getWithdrawOrders(userId, pageIndex, pageSize);
    }


    @GetMapping("/billings")
    @ApiOperation("结算记录")
    public IPage<SettlementCalculateInfo> getBillingRecords(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
                                                            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                                            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ){

        return settlementCalculateResultService.getSettlementCalculateRecords(userId, pageIndex, pageSize);
    }


}
