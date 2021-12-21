package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel("提现帐户信息")
@Data
public class WithdrawAccountInfo {

    /**
     * 可提现金额
     */
    @ApiModelProperty("可提现金额")
    private BigDecimal balance;


    @ApiModelProperty("可提现时间")
    private String withdrawLimit;
}
