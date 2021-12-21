package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("手机号")
public class PhoneNumber {

    /**
     * 手机区号
     */
    @ApiModelProperty("手机区号")
    private String phoneExt;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;


}
