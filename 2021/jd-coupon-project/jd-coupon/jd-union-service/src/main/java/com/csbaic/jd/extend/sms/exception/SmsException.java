package com.csbaic.jd.extend.sms.exception;

/**
 * Created by mouse on 2019/6/17.
 */
public class SmsException extends RuntimeException {

    public SmsException() {
    }

    public SmsException(String message) {
        super(message);
    }

    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsException(Throwable cause) {
        super(cause);
    }

    public SmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
