package com.csbaic.jd.config.application;

import lombok.Data;

/**
 * 微信支付配置
 */
@Data
public class WechatPay {

    /**
     * 证书路径
     */
    private String cert = "classpath:/apiclient_cert.p12";

    /**
     * 应用id
     */
    private String appid = "wxf9ea0760e7c44b57";

    /**
     * API签名
     */
    private String signKey = "替换成自己的密钥";

    /**
     * 商户id
     */
    private String mchid = "1556276181";


    /***
     * 支付回调
     */
    private String notifyUrl;


}
