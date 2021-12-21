package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Coupon {

    /**
     * 券种类 (优惠券种类：0 - 全品类，1 - 限品类（自营商品），2 - 限店铺，3 - 店铺限商品券)
     */

    @ApiModelProperty("券种类 (优惠券种类：0 - 全品类，1 - 限品类（自营商品），2 - 限店铺，3 - 店铺限商品券)")
    private Integer bindType;

    /**
     * 优惠券值折扣
     */
    @ApiModelProperty("优惠券值折扣")
    private Double discount;
    private Long getEndTime;
    private Long getStartTime;

    /**
     * 是否是最好的
     */
    @ApiModelProperty("是否是最好的")
    private Integer isBest;

    /**
     * 优惠券链接
     */
    @ApiModelProperty("优惠券链接")
    private String link;

    /***
     *
     * 券使用平台 (平台类型：0 - 全平台券，1 - 限平台券)
     */
    @ApiModelProperty("券使用平台 (平台类型：0 - 全平台券，1 - 限平台券)")
    private Integer platformType;
    /**
     * 优惠券值面额
     */
    @ApiModelProperty("优惠券值面额")
    private Double quota;
    private Long useEndTime;
    private Long useStartTime;

}