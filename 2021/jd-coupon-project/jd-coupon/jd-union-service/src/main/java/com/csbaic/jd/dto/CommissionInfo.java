package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionInfo {

    /***
     * 佣金
     */
    @ApiModelProperty(value = "佣金")
    private Double commission;

//    /**
//     *
//     * 佣金比例
//     */
//    @ApiModelProperty(value = "佣金比例")
//    private Double commissionShare;

    /**
     *
     * 佣金
     */
    @ApiModelProperty(value = "佣金")
    private Double couponCommission;

    /**
     *
     * 佣金
     */
    @ApiModelProperty(value = "返利提示字段")
    private String rebateHint;


    @ApiModelProperty(value = "会员佣金提示字段")
    private String commissionHint;
}
