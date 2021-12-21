package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CreateWechatUser {


    @ApiModelProperty("登陆时返回的token")
    /**
     * 登陆时返回的token
     */
    private String token;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 手机号加密向量
     */
    @ApiModelProperty("手机号加密向量")
    private String iv;

    /**
     * 邀请码
     */
    @ApiModelProperty("邀请码")
    private String invitationCode;

//
//    @ApiModelProperty("包括敏感数据在内的完整用户信息的加密数据")
//    /***
//     * 包括敏感数据在内的完整用户信息的加密数据
//     */
//    private String encryptedData;

    /***
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatarUrl;


    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;


}
