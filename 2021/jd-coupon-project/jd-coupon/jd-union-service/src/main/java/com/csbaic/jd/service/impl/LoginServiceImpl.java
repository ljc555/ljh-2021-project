package com.csbaic.jd.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csbaic.auth.accesstoken.AccessTokenService;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.config.application.ApplicationProperties;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.entity.WechatUserEntity;
import com.csbaic.jd.extend.authentication.wechat.WechatOpenIdAuthenticationToken;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.IWechatUserService;
import com.csbaic.jd.service.LoginService;
import com.csbaic.jd.service.login.WechatUser;
import com.csbaic.jd.service.login.WechatUserRepository;
import com.csbaic.jd.utils.UserUtils;
import com.csbaic.jd.web.ResultCodes;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService, InitializingBean {


    @Autowired
    private IUserService userService;

    @Autowired
    private IWechatUserService wechatUserService;


    @Autowired
    private AccessTokenService accessTokenService;


    /**
     * 緩存微信用戶,用於註冊時使用
     */
    @Autowired
    private WechatUserRepository wechatUserRepository;




    @Autowired
    private ApplicationProperties properties;



    private OkHttpClient client;


    @Override
    public void afterPropertiesSet() {
        client = new OkHttpClient.Builder()
                .build();
    }

    @Override
    public LoginInfo loginWithOAuth2ByWechat(WechatOAuthLogin wechatLogin) {

        if(wechatLogin == null){
            throw BizRuntimeException.from(ResultCode.ERROR , "微信认证失败");
        }

        if(Strings.isNullOrEmpty(wechatLogin.getCode())){
            throw BizRuntimeException.from(ResultCode.ERROR, "授权失败，缺少code参数");
        }



        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("https://api.weixin.qq.com/sns/oauth2/access_token")
//                .queryParam("state", "csbaic")
                .queryParam("appid", properties.getWebApp().getAppid())
                .queryParam("secret", properties.getWebApp().getSecret())
                .queryParam("response_type", "code")
                .queryParam("code", wechatLogin.getCode())
                .queryParam("grant_type", "authorization_code")
                .build();



        WechatOAuth2AccessToken accessToken;

        Request request = new Request.Builder()
                .get()
                .url(uriComponents.toUriString())
                .build();

        try {
            Response response = client.newCall(request)
                    .execute();

            if(!response.isSuccessful()){
                throw BizRuntimeException.from(ResultCode.ERROR, "微信授权失败");
            }


            accessToken = JSON.parseObject(response.body().string(), WechatOAuth2AccessToken.class);
        } catch (IOException e) {
            log.error("", e);
            throw BizRuntimeException.from(ResultCode.ERROR, "微信授权失败");
        }



        String openId = accessToken.getOpenid();
        String unionId = accessToken.getUnionid();

        if(Strings.isNullOrEmpty(openId) || Strings.isNullOrEmpty(unionId)){
            throw BizRuntimeException.from(ResultCode.ERROR, "缺少OpenId或者UnionId参数");
        }


        WechatUserEntity wechatUserEntity = wechatUserService.getOne(
                Wrappers.<WechatUserEntity>query()
                        .eq(WechatUserEntity.UNION_ID, unionId)
        );


        if(wechatUserEntity == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "未找到用户");
        }


        UserEntity userEntity = userService.getById(wechatUserEntity.getUserId());
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAvatarUrl(userEntity.getAvatarUrl());
        loginInfo.setUserId(userEntity.getId());
        loginInfo.setNickName(userEntity.getNickName());
        loginInfo.setPhone(userEntity.getPhone());
        loginInfo.setInvitationCode(userEntity.getInvitationCode());
        loginInfo.setIdentify(userEntity.getIdentify());
        loginInfo.setIdentifyDesc(UserUtils.getUserIdentify(userEntity.getIdentify()).getName());

        Map<String, Object> params = Maps.newHashMap();
        params.put("user_id", loginInfo.getUserId());
        params.put("open_id", wechatUserEntity.getOpenId());

        String token = accessTokenService.encode(params);
        loginInfo.setToken(token);

        return loginInfo;
    }

    @Override
    public String createWechatOAuth2AuthorizationUrl(String redirectUrl) {
        //https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
        return UriComponentsBuilder
                .fromUriString("https://open.weixin.qq.com/connect/qrconnect")
//                .queryParam("state", "csbaic")
                .queryParam("appid", properties.getWebApp().getAppid())
                .queryParam("redirect_uri", Strings.isNullOrEmpty(redirectUrl) ? properties.getWebApp().getRedirectUri() : redirectUrl)
                .queryParam("response_type", "code")
                .queryParam("scope", "snsapi_login")
                .queryParam("state", "")
                .toUriString();


    }

    /**
     * 使用微信注册
     * @param wechatLogin
     * @return
     */
    @Transactional(noRollbackFor = NotRegisteredWechatUserException.class)
    @Override
    public LoginInfo loginByWechat(WechatLogin wechatLogin) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new BizRuntimeException("微信认证失败");
        }

        if(!(authentication instanceof WechatOpenIdAuthenticationToken)){
            throw  BizRuntimeException.from(ResultCode.ERROR, "认证令牌信息错误");
        }

        WechatOpenIdAuthenticationToken authenticationToken = (WechatOpenIdAuthenticationToken) authentication;
        WechatUserEntity wechatUserEntity = wechatUserService.getOne(
                Wrappers.<WechatUserEntity>query()
                .eq(WechatUserEntity.OPEN_ID, ((WechatOpenIdAuthenticationToken) authentication).getOpenId())
        );

        boolean isNewUser = false;

        if(wechatUserEntity != null){
            UserEntity userEntity = userService.getById(wechatUserEntity.getUserId());
            if (userEntity == null) {
                //删除数据库中的微信用户，重新注册
                wechatUserService.removeById(wechatUserEntity.getId());

                //如果有微信信息，但是通过微信关联不到用户，可能是用户已经被删除
                //需要重新注册
                isNewUser = true;
            }
        }else{
            isNewUser = true;
        }


        /*
            此微信还没有注册，需要提示去注册
         */
        if(isNewUser){
            //臨時保存微信認證信息，下一步註冊後存入數據庫
            WechatUser wechatUser = new WechatUser();
            wechatUser.setOpenId(authenticationToken.getOpenId());
            wechatUser.setSessionKey(authenticationToken.getSessionKey());
            wechatUser.setUnionId(authenticationToken.getUnionid());
            wechatUserRepository.save(wechatUser);


            /*
                返回用户未注册错误码，小程序查看判断错误码进行注册（带上openId）
             */
            throw new NotRegisteredWechatUserException(ResultCodes.USER_NOT_REGISTERED.getCode(),"未注册的微信用户", new Token(authenticationToken.getOpenId()));
        }


        //更新登陆信息
        wechatUserEntity.setUnionId(authenticationToken.getUnionid());
        wechatUserEntity.setSessionKey(authenticationToken.getSessionKey());
        wechatUserService.updateById(wechatUserEntity);

        UserEntity userEntity = userService.getById(wechatUserEntity.getUserId());
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAvatarUrl(userEntity.getAvatarUrl());
        loginInfo.setUserId(userEntity.getId());
        loginInfo.setNickName(userEntity.getNickName());
        loginInfo.setPhone(userEntity.getPhone());
        loginInfo.setInvitationCode(userEntity.getInvitationCode());
        loginInfo.setIdentify(userEntity.getIdentify());
        loginInfo.setIdentifyDesc(UserUtils.getUserIdentify(userEntity.getIdentify()).getName());

        Map<String, Object> params = Maps.newHashMap();
        params.put("user_id", loginInfo.getUserId());
        params.put("open_id", wechatUserEntity.getOpenId());

        String token = accessTokenService.encode(params);
        loginInfo.setToken(token);

        return loginInfo;
    }


    public static class NotRegisteredWechatUserException extends BizRuntimeException {

        public NotRegisteredWechatUserException(String message) {
            super(message);
        }

        public NotRegisteredWechatUserException(String errorCode, String message) {
            super(errorCode, message);
        }

        public NotRegisteredWechatUserException(String errorCode, String message, Object data) {
            super(errorCode, message, data);
        }

        public NotRegisteredWechatUserException(Throwable cause) {
            super(cause);
        }
    }

}
