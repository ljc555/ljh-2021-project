package com.csbaic.jd.extend.authentication.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.google.common.base.Strings;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;

/**
 * 微信认证
 */
public class WechatAuthenticationProvider implements AuthenticationProvider {


    /**
     * 微信api服务
     */
    private final WxMaService wxMaService;

    public WechatAuthenticationProvider(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WechatAuthenticationToken wechatAuthenticationToken = (WechatAuthenticationToken) authentication;


        if(Strings.isNullOrEmpty(wechatAuthenticationToken.getCode())){
            throw new BadAuthenticationCodeException("微信认证code错误：" + wechatAuthenticationToken.getCode());
        }

        try {
            WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(wechatAuthenticationToken.getCode());
            return new WechatOpenIdAuthenticationToken(new ArrayList<>(),result.getOpenid(), result.getSessionKey(), result.getUnionid());
        } catch (WxErrorException e) {
            throw new WechatAuthenticationException("获取openId错误: " + e.getError().getErrorMsg());
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
