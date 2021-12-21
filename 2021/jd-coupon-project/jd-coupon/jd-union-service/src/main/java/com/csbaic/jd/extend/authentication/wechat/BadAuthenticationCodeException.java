package com.csbaic.jd.extend.authentication.wechat;

import org.springframework.security.core.AuthenticationException;

public class BadAuthenticationCodeException extends AuthenticationException {

    public BadAuthenticationCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public BadAuthenticationCodeException(String msg) {
        super(msg);
    }
}
