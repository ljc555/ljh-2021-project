package com.csbaic.jd.extend.sms.exception;

/**
 * 短信验证异常
 */
public class SmsValidateException extends RuntimeException {

    public SmsValidateException() {
        super();
    }

    public SmsValidateException(String message) {
        super(message);
    }

    public SmsValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsValidateException(Throwable cause) {
        super(cause);
    }

    protected SmsValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
