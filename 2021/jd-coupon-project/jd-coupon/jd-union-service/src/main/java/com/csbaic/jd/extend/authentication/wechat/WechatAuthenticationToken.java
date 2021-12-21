package com.csbaic.jd.extend.authentication.wechat;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class WechatAuthenticationToken extends AbstractAuthenticationToken {


    private final String code;


    /**
     *
     * @param code 登陆登陆code
     * @param authorities
     */
    public WechatAuthenticationToken(String code, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.code = code;
    }

    /**
     * 登陆code登陆
     * @param code
     */
    public WechatAuthenticationToken(String code ) {
        this(code, new ArrayList<>());
    }


    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return "";
    }

    public String getCode() {
        return code;
    }
}
