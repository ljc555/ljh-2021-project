package com.csbaic.jd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WechatOAuthLogin {

    private String code;

    private String state;
}
