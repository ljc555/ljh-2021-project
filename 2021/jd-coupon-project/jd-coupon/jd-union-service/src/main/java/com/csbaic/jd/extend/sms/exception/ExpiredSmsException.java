package com.csbaic.jd.extend.sms.exception;

/**
 * 短信已经过期
 */
public class ExpiredSmsException extends SmsValidateException {

    public ExpiredSmsException() {
        super();
    }

    public ExpiredSmsException(String message) {
        super(message);
    }

    public ExpiredSmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredSmsException(Throwable cause) {
        super(cause);
    }

    protected ExpiredSmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
