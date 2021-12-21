package com.csbaic.jd.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.csbaic.jd.config.application.ApplicationProperties;
import com.csbaic.jd.config.application.WechatPay;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by yjwfn on 2020/2/14.
 */
@Configuration
public class WechatConfiguration {


    @Bean
    public WxMaService wxMaService(ApplicationProperties properties){

        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(properties.getMiniApp().getAppid());
        config.setSecret(properties.getMiniApp().getSecret());
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);

        return service;
    }

    @Bean
    public WxPayService wxPayService(ApplicationProperties properties){
        WechatPay wechatPay = properties.getWechatPay();
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setKeyPath(wechatPay.getCert());
        payConfig.setMchKey(wechatPay.getSignKey());
        payConfig.setAppId(wechatPay.getAppid());
        payConfig.setMchId(wechatPay.getMchid());
//        payConfig.setNotifyUrl(wechatPay.getNotifyUrl());

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);

        return wxPayService;
    }
//
//    @Bean
//    public WxMpService wxMpService(ApplicationProperties properties){
//
//        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
//        configStorage.setAppId(properties.getWebApp().getAppid());
//        configStorage.setSecret(properties.getWebApp().getSecret());
//        WxMpService wxMpService = new WxMpServiceImpl();
//        wxMpService.setWxMpConfigStorage(configStorage);
//
//        return wxMpService;
//    }

}
