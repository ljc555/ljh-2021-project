package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.LoginInfo;
import com.csbaic.jd.dto.OAuthAuthorizeUrl;
import com.csbaic.jd.dto.WechatLogin;
import com.csbaic.jd.dto.WechatOAuthLogin;
import com.csbaic.jd.service.LoginService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登陆")
@RestController
@Controller
@RequestMapping("/login")
@ResponseResult
public class LoginController {

    @Autowired
    private LoginService loginService;


    @ResponseResult
    @ApiOperation("微信小程序登陆")
    @PostMapping("/wechat")
    public LoginInfo loginByWechat(@RequestBody WechatLogin wechatLogin){
        return loginService.loginByWechat(wechatLogin);
    }



    @ApiOperation("获取微信网站应用授权地址")
    @GetMapping("/wechat/web/authorize_url")
    public OAuthAuthorizeUrl getWechatWebAuthorizeUrl(@RequestParam(value = "redirect_url", required = false) String url){
        String authorizationUrl =  loginService.createWechatOAuth2AuthorizationUrl(url);
        return new OAuthAuthorizeUrl(authorizationUrl);
    }


    @ResponseResult
    @ApiOperation("微信Web登陆")
    @GetMapping("/wechat/web")
    public LoginInfo getAccessTokenByWechatCode(@RequestParam("code") String code, @RequestParam(value = "state", required = false) String state){
        return loginService.loginWithOAuth2ByWechat(new WechatOAuthLogin(code, state));
    }
}
