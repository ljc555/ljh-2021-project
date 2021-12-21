package com.csbaic.jd.extend.sms.ali;


public class AliSmsConfig {

    private final String accessKeyId;
    private final String accessSecret;
    private final String signName;
    private final String templateCode;

    public AliSmsConfig(String accessKeyId, String accessSecret, String signName, String templateCode) {
        this.accessKeyId = accessKeyId;
        this.accessSecret = accessSecret;
        this.signName = signName;
        this.templateCode = templateCode;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public String getSignName() {
        return signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }
}
