package com.csbaic.jd.extend.sms.exception;

/**
 * 短信发送太频繁
 */
public class FrequentLimitException extends SmsException {

    public FrequentLimitException() {
        super();
    }

    public FrequentLimitException(String message) {
        super(message);
    }

    public FrequentLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrequentLimitException(Throwable cause) {
        super(cause);
    }

    public FrequentLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
