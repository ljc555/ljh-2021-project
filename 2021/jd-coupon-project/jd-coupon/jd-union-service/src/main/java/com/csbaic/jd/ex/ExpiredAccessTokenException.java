package com.csbaic.jd.ex;

/**
 * AccessToken异常
 */
public class ExpiredAccessTokenException extends RuntimeException{


    public ExpiredAccessTokenException() {
    }

    public ExpiredAccessTokenException(String message) {
        super(message);
    }

    public ExpiredAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredAccessTokenException(Throwable cause) {
        super(cause);
    }

    public ExpiredAccessTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
