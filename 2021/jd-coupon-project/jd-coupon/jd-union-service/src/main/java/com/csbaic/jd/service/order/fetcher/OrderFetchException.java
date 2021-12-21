package com.csbaic.jd.service.order.fetcher;

public class OrderFetchException extends Exception{

    public OrderFetchException() {
        super();
    }

    public OrderFetchException(String message) {
        super(message);
    }

    public OrderFetchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderFetchException(Throwable cause) {
        super(cause);
    }

    protected OrderFetchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
