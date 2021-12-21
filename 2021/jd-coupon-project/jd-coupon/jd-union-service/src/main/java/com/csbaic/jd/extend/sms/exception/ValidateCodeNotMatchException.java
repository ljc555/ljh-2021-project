package com.csbaic.jd.extend.sms.exception;

/**
 * 短信发送太频繁
 */
public class ValidateCodeNotMatchException extends SmsValidateException {

    public ValidateCodeNotMatchException() {
        super();
    }

    public ValidateCodeNotMatchException(String message) {
        super(message);
    }

    public ValidateCodeNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateCodeNotMatchException(Throwable cause) {
        super(cause);
    }

    public ValidateCodeNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
