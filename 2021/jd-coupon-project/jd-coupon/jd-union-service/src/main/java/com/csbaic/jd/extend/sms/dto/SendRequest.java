package com.csbaic.jd.extend.sms.dto;

/**
 * 发送短信请求
 */
public interface SendRequest {

    /**
     * 获取手机区号
     * @return
     */
    String getPhoneExt();

    /**
     * 获取手机号
     * @return
     */
    String getPhone();

}
