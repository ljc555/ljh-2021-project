package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CreateGoodsQRCode {

    /**
     * 商品链接
     */
    @ApiModelProperty("商品链接")
    private String materialUrl;

    /**
     * 优惠券链接
     */
    @ApiModelProperty("优惠券链接")
    private String couponUrl;

    /**
     * 二维宽高大小
     */
    @ApiModelProperty("二维宽高大小")
    private Integer size;

}
