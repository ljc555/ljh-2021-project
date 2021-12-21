package com.csbaic.jd.extend.sms.dto;

import com.csbaic.jd.extend.sms.repository.SmsInfo;

import java.util.HashMap;

/**
 * 使用模版发送短信请求
 */
public interface TemplateSendRequest extends SendRequest {

    /**
     * 模版
     * @return
     */
    String getTemplate();

    /**
     * 模版参数
     * @return
     */
    HashMap<String, String> getTemplateParams();
}
