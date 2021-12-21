package com.csbaic.jd.extend.sms.validate;

public class ValidateArgs {

    private  final String phoneExt;
    private  final String phone;
    private  final String code;
    private  final String token;
    private  final String bizId;


    public ValidateArgs(String phoneExt, String phone, String code, String token, String bizId) {
        this.phoneExt = phoneExt;
        this.phone = phone;
        this.code = code;
        this.token = token;
        this.bizId = bizId;
    }

    public ValidateArgs(String phoneExt, String phone, String code,  String bizId) {
        this(phoneExt, phone, code, null, bizId);
    }

    public String getBizId() {
        return bizId;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "ValidateArgs{" +
                "phoneExt='" + phoneExt + '\'' +
                ", phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                ", token='" + token + '\'' +
                ", bizId='" + bizId + '\'' +
                '}';
    }
}
