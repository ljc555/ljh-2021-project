package com.csbaic.jd.service;

import com.csbaic.jd.dto.LoginInfo;
import com.csbaic.jd.dto.WechatLogin;
import com.csbaic.jd.dto.WechatOAuthLogin;

public interface LoginService {

    /**
     * 微信登陆
     * @param wechatLogin
     * @return
     */
    LoginInfo loginByWechat(WechatLogin wechatLogin);


    /**
     * 管理员登陆
     * @param wechatLogin
     * @return
     */
    LoginInfo loginWithOAuth2ByWechat(WechatOAuthLogin wechatLogin);


    /**
     * 生成微信登陆URL
     * @param
     * @return
     */
    String createWechatOAuth2AuthorizationUrl(String url);
}
