package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopInfo {
    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /***
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;
}
