package com.csbaic.jd.dto;

/**
 * 发送短信
 */
public class SmsSentResult {

    /**
     * 短信token
     */
    private final String token;

    public SmsSentResult(String token) {
        this.token = token;
    }
}
