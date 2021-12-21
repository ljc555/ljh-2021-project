package com.csbaic.jd.config.application;

import lombok.Data;

/**
     * App 属性配置
     */
@Data
public  class ApplicationProperties {

    /**
     * jwt token生成key
     */
    private String jwtKeyValue;

    /**
     * api文档访问地址
     */
    private String apiHost = "localhost";

    /**
     * 小程序配置
     */
    private MiniApp miniApp = new MiniApp();

    /**
     * 微信网站应用
     */
    private WebApp webApp = new WebApp();

    /***
     * 微信支付
     */
    private WechatPay WechatPay  = new WechatPay();

    /**
     * 短信配置
     */
    private AliSms sms = new AliSms();


    /**
     * 商品二维码图片位置
     */
    private String goodsQrcodeLogo;



}
