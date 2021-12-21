package com.csbaic.jd.extend.sms.dto;

import java.util.HashMap;

class SimpleTemplateSendRequest implements TemplateSendRequest {

    private final String phoneExt;
    private final String phone;
    private final String template;
    private final HashMap<String, String> templateParams;

    public SimpleTemplateSendRequest(String phoneExt, String phone, String template, HashMap<String, String> templateParams) {
        this.phoneExt = phoneExt;
        this.phone = phone;
        this.template = template;
        this.templateParams = templateParams;
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
    public String getTemplate() {
        return template;
    }

    @Override
    public HashMap<String, String> getTemplateParams() {
        return templateParams;
    }

    @Override
    public String toString() {
        return "SimpleTemplateSendRequest{" +
                "phoneExt='" + phoneExt + '\'' +
                ", phone='" + phone + '\'' +
                ", template='" + template + '\'' +
                ", templateParams=" + templateParams +
                '}';
    }
}
