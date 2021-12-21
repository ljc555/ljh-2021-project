package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "更新用户信息")
@Data
public class UpdateUserInfo {

    /**
     * 用户微信id
     */
    private String wechatId;

    /**
     * 昵称
     */
    private String nickName;


    /**
     * 头像Url
     */
    private String avatarUrl;


    /**
     * 手机号
     */
    private String phone;

}
