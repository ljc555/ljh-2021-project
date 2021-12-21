package com.csbaic.jd.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.UpdateWithDrawOrder;
import com.csbaic.jd.dto.WithdrawOrderInfo;
import com.csbaic.jd.service.IApplyWithdrawOrderService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(value = "管理钱包", tags = "钱包管理")
@ResponseResult
public class AdminWalletController {


    @Autowired
    private IApplyWithdrawOrderService withdrawOrderService;



    @PutMapping("/wallet/apply_withdraw")
    @ApiOperation("更新申请提现单状态")
    public void updateApplyWithdraw(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody UpdateWithDrawOrder info){
        withdrawOrderService.updateApplyWithdrawOrder(userId,info);
    }


    @GetMapping("/wallet/apply_withdraw")
    @ApiOperation("获取申请提现单")
    public IPage<WithdrawOrderInfo> updateApplyWithdraw(
            @ApiParam(value = "申请单状态", allowableValues = "range[1, 9]")
            @RequestParam(value = "status", required = false) Integer status,
                                                        @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize){
        return withdrawOrderService.getWithdrawOrders(pageIndex, pageSize, status);
    }



    /**
     * 添加一条
     */
    @ApiOperation("零钱打款")
    @PostMapping("/wallet/withdraw_payment/{id}")
    public void pay(
            @ApiParam(hidden = true)
            @AuthenticationPrincipal(expression = "id") Long userId,  @PathVariable("id") Long orderId){
        withdrawOrderService.payForWithdraw(userId, orderId);
    }


}
