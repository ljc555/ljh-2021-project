package com.csbaic.jd.service.sms;

public enum SmsBiz {
    /*
        验证手机号
     */
    VALIDATE_PHONE("SMS_182910815"),

    /*
      提现
    */
    APPLY_WITHDRAW("SMS_182910815");

    private final String template;

    SmsBiz(String template) {
        this.template = template;
    }


    public String getTemplate() {
        return template;
    }

}
