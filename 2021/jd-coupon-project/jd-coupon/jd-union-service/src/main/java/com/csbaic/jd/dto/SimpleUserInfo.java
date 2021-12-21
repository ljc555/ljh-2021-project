package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class SimpleUserInfo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户手机号
     */
    private String avatarUrl;
}
