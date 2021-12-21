package com.csbaic.jd.extend.sms.client;

import com.csbaic.jd.extend.sms.dto.SendRequest;
import com.csbaic.jd.extend.sms.dto.TemplateSendRequest;
import com.csbaic.jd.extend.sms.exception.SmsException;

/**
 * 发送短信客户端
 */
public interface SmsClient {

    /**
     * 发送短信
     * @param request
     */
    void send(SendRequest request) throws SmsException;

    /**
     * 使用短信模版发送短信
     * @param request
     */
    void send(TemplateSendRequest request) throws SmsException;
}
