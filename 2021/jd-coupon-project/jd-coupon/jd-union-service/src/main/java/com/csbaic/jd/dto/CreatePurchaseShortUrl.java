package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreatePurchaseShortUrl {


    /**
     * 商品满地页
     */
    @ApiModelProperty(" 商品满地页")
    private String materialId;

    /**
     * 优惠券url
     */
    @ApiModelProperty("优惠券url")
    private String couponUrl;

    /**
     * 推广人邀请码
     */
    @ApiModelProperty(" 推广人邀请码，为空则为当前登陆用户")
    private String invitationCode;

}
