package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ConvertUrlContent {

    /***
     * 内容
     */
    @ApiModelProperty("需要转换的内容")
    private String content;

//    /**
//     * 推广人邀请码
//     */
//    @ApiModelProperty(" 推广人邀请码，为空则为当前登陆用户")
//    private String invitationCode;


}
