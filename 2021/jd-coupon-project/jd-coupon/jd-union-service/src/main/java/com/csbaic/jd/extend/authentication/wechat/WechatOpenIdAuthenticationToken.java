package com.csbaic.jd.extend.authentication.wechat;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WechatOpenIdAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 微信openId
     */
    private final String openId;

    /**
     * 微信会话密钥
     */
    private final String sessionKey;


    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
     */
    private final String unionid;

    public WechatOpenIdAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String openId, String sessionKey, String unionid) {
        super(authorities);
        this.openId = openId;
        this.sessionKey = sessionKey;
        this.unionid = unionid;
    }

    @Override
    public Object getCredentials() {
        return sessionKey;
    }

    @Override
    public Object getPrincipal() {
        return openId;
    }

    public String getOpenId() {
        return openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getUnionid() {
        return unionid;
    }
}
