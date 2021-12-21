package com.csbaic.jd.extend.sms.client;

import com.csbaic.jd.extend.sms.dto.SendRequest;
import com.csbaic.jd.extend.sms.dto.TemplateSendRequest;
import com.csbaic.jd.extend.sms.exception.SmsException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoOpSmsClient implements SmsClient {

    @Override
    public void send(SendRequest request) throws SmsException {
        log.info("send　{}", request);
    }

    @Override
    public void send(TemplateSendRequest request) throws SmsException {
        log.info("send　{}", request);
    }
}
