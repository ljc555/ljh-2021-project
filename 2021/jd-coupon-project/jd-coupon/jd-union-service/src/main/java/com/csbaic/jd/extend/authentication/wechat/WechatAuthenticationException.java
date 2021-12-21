package com.csbaic.jd.extend.authentication.wechat;

import org.springframework.security.core.AuthenticationException;

public class WechatAuthenticationException extends AuthenticationException {

    public WechatAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public WechatAuthenticationException(String msg) {
        super(msg);
    }
}
