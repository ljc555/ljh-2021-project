package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 二维码
 */
@Data
@ApiModel
public class Base64QRCode {

    /**
     * 二维码（base64编码）
     */
    @ApiModelProperty("二维码（base64编码）")
    private String content;

}
