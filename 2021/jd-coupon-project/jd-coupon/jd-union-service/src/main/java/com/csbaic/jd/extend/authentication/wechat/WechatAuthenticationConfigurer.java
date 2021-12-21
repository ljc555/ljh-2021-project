package com.csbaic.jd.extend.authentication.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;


public class WechatAuthenticationConfigurer extends AbstractHttpConfigurer<WechatAuthenticationConfigurer, HttpSecurity> {

    /**
     * 使用http调用code2session接口
     */
    private WxMaService wxMaService;


    private RequestMatcher requiresAuthenticationRequestMatcher;


    /**
     * 认证失败处理器
     */
    private AuthenticationFailureHandler failureHandler;


    @Override
    public void init(HttpSecurity builder) throws Exception {
        //获取 AuthenticationManager

    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {

        builder
                .authenticationProvider(wechatAuthenticationProvider(wxMaService))
                .addFilterBefore(wechatAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


    }


    WechatAuthenticationProvider wechatAuthenticationProvider(WxMaService wxMaService){
        WechatAuthenticationProvider provider = new WechatAuthenticationProvider(wxMaService);
        return provider;
    }



    WechatAuthenticationFilter wechatAuthenticationFilter(){

        AuthenticationManager authenticationManager = getBuilder().getSharedObject(AuthenticationManager.class);
        if(authenticationManager == null){
            throw new IllegalStateException("Not found AuthenticationManager");
        }

        if(requiresAuthenticationRequestMatcher == null){
            throw new IllegalStateException("requiresAuthenticationRequestMatcher == null");
        }


        WechatAuthenticationFilter filter =  new WechatAuthenticationFilter(requiresAuthenticationRequestMatcher);
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }


    public WechatAuthenticationConfigurer requiresAuthenticationRequestMatcher(RequestMatcher requiresAuthenticationRequestMatcher) {
        this.requiresAuthenticationRequestMatcher = requiresAuthenticationRequestMatcher;
        return this;
    }

    public WechatAuthenticationConfigurer wxMaService(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
        return this;
    }

    public WechatAuthenticationConfigurer failureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
        return this;
    }
}
