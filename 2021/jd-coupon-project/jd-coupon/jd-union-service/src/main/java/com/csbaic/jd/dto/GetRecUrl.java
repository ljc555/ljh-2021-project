package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetRecUrl {

    /**
     * 商品满地页
     */
    @ApiModelProperty(" 商品满地页")
    private String materialId;

    /**
     * 子联盟id
     */
    @ApiModelProperty("子联盟id")
    private String subUnionId;

    /**
     * 优惠券url
     */
    @ApiModelProperty("优惠券url")
    private String couponUrl;
    /**
     * 转链类型，1：长链， 2 ：短链 ，3： 长链+短链，默认短链
     */
    @ApiModelProperty(value = "转链类型，1：长链， 2 ：短链 ，3： 长链+短链，默认短链", allowableValues = "1,2,3")
    private Integer chainType;


    private String  giftCouponKey;
}
