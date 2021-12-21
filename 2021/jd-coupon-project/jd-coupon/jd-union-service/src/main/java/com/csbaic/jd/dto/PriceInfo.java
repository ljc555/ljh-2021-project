package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceInfo {

    /**
     * 无线价格
     */
    @ApiModelProperty("无线价格")
    private Double price;
    /***
     * 最低价格
     */
    @ApiModelProperty("最低价格")
    private Double lowestPrice;
    /**
     * 最低价格类型，1：无线价格；2：拼购价格； 3：秒杀价格
     */
    @ApiModelProperty("最低价格类型，1：无线价格；2：拼购价格； 3：秒杀价格")
    private Integer lowestPriceType;

    @ApiModelProperty("使用优惠券的最低价")
    private Double lowestCouponPrice;

    @ApiModelProperty("返利价")
    private Double lowestRebatePrice;
}
