package com.csbaic.jd.extend.sms.repository;


public class SimpleSmsInfo implements SmsInfo {

    private final String phoneExt;
    private final String phone;
    private final String bizId;
    private final String code;

    public SimpleSmsInfo(String phoneExt, String phone, String bizId, String code) {
        this.phoneExt = phoneExt;
        this.phone = phone;
        this.bizId = bizId;
        this.code = code;
    }

    @Override
    public String getPhoneExt() {
        return phoneExt;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getBizId() {
        return bizId;
    }

    @Override
    public String getCode() {
        return code;
    }
}
