package com.csbaic.jd.extend.sms.ali;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.csbaic.jd.extend.sms.client.SmsClient;
import com.csbaic.jd.extend.sms.dto.SendRequest;
import com.csbaic.jd.extend.sms.dto.TemplateSendRequest;
import com.csbaic.jd.extend.sms.exception.FrequentLimitException;
import com.csbaic.jd.extend.sms.exception.SmsException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Created by mouse on 2019/5/23.
 */
@Slf4j
public class AliyunSmsClient implements SmsClient {

    private final AliSmsConfig config;


    public AliyunSmsClient(AliSmsConfig config) {
        this.config = config;

        if(config == null){
            throw new IllegalStateException("config == null");
        }
    }


    @Override
    public void send(SendRequest request) {
        if(request instanceof TemplateSendRequest){
            send((TemplateSendRequest) request);
            return;
        }

        throw new IllegalStateException("不支持的发送请求：" + request.getClass().getName());
    }

    @Override
    public void send(TemplateSendRequest smsReq) {

        DefaultProfile profile = DefaultProfile.getProfile("default", config.getAccessKeyId(), config.getAccessSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", smsReq.getPhone());
        request.putQueryParameter("SignName", config.getSignName());
        request.putQueryParameter("TemplateCode", smsReq.getTemplate());
        request.putQueryParameter("TemplateParam", JSON.toJSONString(smsReq.getTemplateParams()));
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info(response.getData());

            HashMap map = JSON.parseObject(response.getData(), HashMap.class);
            String code = (String) map.get("Code");
            if (!code.equals("OK")) {
//                String message = (String) map.get("Message");
                if ("isv.BUSINES_LIMIT_CONTROL".equals(code)) {
                    throw new FrequentLimitException("短信过送过于频繁");
                } else {
                    throw new SmsException();
                }
            }
        } catch (ClientException e) {
            log.warn(e.getMessage());
            throw new SmsException(e);
        }

    }
}
