package com.csbaic.jd.extend.authentication.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csbaic.auth.web.servlet.ReusableContentCachingRequestWrapper;
import com.google.common.base.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 获取认证
 */
public class WechatAuthenticationFilter implements javax.servlet.Filter {

    /**
     * 微信认证key
     */
    private static final String KEY_CODE = "code";

    private   final RequestMatcher requestMatcher;

    private   AuthenticationManager authenticationManager;

    /**
     * 认证失败处理器
     */
    private AuthenticationFailureHandler authenticationFailureHandler;

    public WechatAuthenticationFilter(RequestMatcher matcher) {
         this.requestMatcher = matcher;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        if(!requestMatcher.matches((HttpServletRequest) servletRequest)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        String code;
        ServletRequest cacheServletRequest = new ReusableContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        try{
            code = obtainWechatCode((HttpServletRequest) cacheServletRequest);

            if(Strings.isNullOrEmpty(code)){
                throw new BadAuthenticationCodeException("缺少微信授权code");
            }

            Authentication authentication = authenticationManager.authenticate(new WechatAuthenticationToken(code));

            if(authentication != null){
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }catch (Exception e){
           unsuccessfulAuthentication((HttpServletRequest) servletRequest,(HttpServletResponse) servletResponse , new BadAuthenticationCodeException("微信认证失败: " + e.getMessage(), e));
            return;
        }

        filterChain.doFilter(cacheServletRequest, servletResponse);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        SecurityContextHolder.clearContext();
        authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }

    private static String obtainWechatCode(HttpServletRequest request) throws Exception{

        StringBuilder sb = new StringBuilder();
        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONObject object = JSON.parseObject(sb.toString());
        return object.getString(KEY_CODE);
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
