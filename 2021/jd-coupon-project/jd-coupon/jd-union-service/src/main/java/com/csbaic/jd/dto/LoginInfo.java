package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class LoginInfo {


    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatarUrl;

    /***
     * 用户手机号
     */
    private String phone;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 用户身份（导师，合伙人）
     */
    private Integer identify;

    /**
     * 用户身份（导师，合伙人）
     */
    private String identifyDesc;

    /**
     * token
     */
    private String token;
}
