package com.csbaic.jd.service.login;

public interface WechatUserRepository {

    /**
     * 獲取微信用戶
     * @param openId 開放平臺id
     * @return
     */
    WechatUser get(String openId);

    /**
     * 保存微信用戶id
     * @param user
     */
    void save(WechatUser user);

}
