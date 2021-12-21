package com.csbaic.jd.service.login;

public class WechatUser {

    /**
     * 微信會話
     */
    private String sessionKey;

    /***
     * 微信開放id
     */
    private String openId;

    /**
     * 微信聯盟id
     */
    private String unionId;


    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
