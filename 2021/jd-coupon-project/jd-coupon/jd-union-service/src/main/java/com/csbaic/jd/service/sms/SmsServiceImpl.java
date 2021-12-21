package com.csbaic.jd.service.sms;

import com.csbaic.jd.dto.SmsSentResult;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.extend.sms.client.SmsClient;
import com.csbaic.jd.extend.sms.dto.SendRequest;
import com.csbaic.jd.extend.sms.dto.SendRequestBuilder;
import com.csbaic.jd.extend.sms.exception.SmsException;
import com.csbaic.jd.extend.sms.exception.SmsValidateException;
import com.csbaic.jd.extend.sms.gen.SmsValidateCodeGenerator;
import com.csbaic.jd.extend.sms.repository.SimpleSmsInfo;
import com.csbaic.jd.extend.sms.repository.SmsRepository;
import com.csbaic.jd.extend.sms.validate.SmsCodeValidator;
import com.csbaic.jd.extend.sms.validate.ValidateArgs;
import com.csbaic.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService{

    @Autowired
    private SmsClient smsClient;

    @Autowired
    private SmsRepository smsRepository;

    @Autowired
    private SmsValidateCodeGenerator generator;

    @Autowired
    private SmsCodeValidator validator;

    @Override
    public SmsSentResult send(String phoneExt, String phone, SmsBiz bizId) {
        log.info("send : ext({}), phone({}}), bizId({})", phoneExt, phone, bizId);

        String code = generator.generate();
        SendRequest sendRequest = new SendRequestBuilder()
                .phone(phone)
                .phoneExt(phoneExt)
                .template(bizId.getTemplate())
                .param("code", code )
                .build();


        try{
            smsClient.send(sendRequest);
            String token = smsRepository.save(new SimpleSmsInfo(phoneExt, phone, bizId.name(), code));
            return new SmsSentResult(token);
        }catch (SmsException e){
            throw BizRuntimeException.from(ResultCode.ERROR, e.getMessage());
        }
    }

    @Override
    public void validate(ValidateArgs args) {
        log.info("validate : ", args);
        try {
            validator.validate(args);
        }catch (SmsValidateException e){
            throw BizRuntimeException.from(ResultCode.ERROR, e.getMessage());
        }
    }
}
