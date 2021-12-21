package com.csbaic.jd.extend.sms.dto;

import java.util.HashMap;

public class SendRequestBuilder {

    private  String phoneExt;
    private  String phone;
    private  String template;
    private final HashMap<String, String> templateParams = new HashMap<>();


    public SendRequestBuilder phoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
        return this;
    }

    public SendRequestBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public SendRequestBuilder template(String template) {
        this.template = template;
        return this;
    }

    public SendRequestBuilder param(String key, String value) {
        this.templateParams.put(key, value);
        return this;
    }

    public SendRequest build(){
        return new SimpleTemplateSendRequest(phoneExt, phone, template, templateParams);
    }
}
