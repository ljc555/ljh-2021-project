package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateGoodsShortUrl {


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



}
