package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleMemberUserInfo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 用户名称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户微信
     */
    private String wechatId;

    /**
     * 用户等级
     */
    private Integer identify;

    /**
     * 等级文本
     */
    private String identifyDesc;

    /**
     * 活跃度
     */
    private Integer activeScore;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;
}
