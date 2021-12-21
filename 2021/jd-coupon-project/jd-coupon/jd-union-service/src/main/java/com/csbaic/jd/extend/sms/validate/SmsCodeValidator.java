package com.csbaic.jd.extend.sms.validate;

import com.csbaic.jd.extend.sms.exception.SmsValidateException;

/**
 * 短信验证器
 */
public interface SmsCodeValidator {

    /**
     * 验证短信
     * @param validateArgs 短信token
     * @throws SmsValidateException
     */
    void validate(ValidateArgs validateArgs) throws SmsValidateException;
}
