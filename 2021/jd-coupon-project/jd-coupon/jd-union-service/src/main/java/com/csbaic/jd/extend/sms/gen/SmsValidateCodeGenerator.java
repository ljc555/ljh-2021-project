package com.csbaic.jd.extend.sms.gen;

/**
 * 短信验证码生成器
 */
public interface SmsValidateCodeGenerator {

    /**
     * 生成短信验证码
     * @return
     */
    String generate();

}
