package com.csbaic.jd.extend.sms.repository;

/**
 * 已发送的验证码
 */
public interface SmsInfo {

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


    /**
     * 业务码
     * @return
     */
    String getBizId();


    /**
     * 获取验证码
     * @return
     */
    String getCode();


}
