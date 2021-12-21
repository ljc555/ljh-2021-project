package com.csbaic.jd.extend.sms.validate;

import com.csbaic.jd.extend.sms.exception.SmsValidateException;
import com.csbaic.jd.extend.sms.exception.ValidateCodeNotMatchException;
import com.csbaic.jd.extend.sms.repository.SmsInfo;
import com.csbaic.jd.extend.sms.repository.SmsRepository;
import com.google.common.base.Strings;

import java.util.Objects;

public class MatchTokenSmsCodeValidator implements SmsCodeValidator {


    /**
     * 短信存在库
     */
    private final SmsRepository repository;

    public MatchTokenSmsCodeValidator(SmsRepository repository) {
        this.repository = repository;
    }


    @Override
    public void validate(ValidateArgs validateArgs) throws SmsValidateException {

        SmsInfo info = repository.get(validateArgs.getToken());

        if(info == null){
            throw new SmsValidateException("短信未找到或已过期");
        }
        String sent = info.getCode();

        if(Strings.isNullOrEmpty(sent)){
            throw new SmsValidateException("短信验证码未找到");
        }

        if(!Objects.equals(info.getBizId(), validateArgs.getBizId())){
            throw new SmsValidateException("业务Id不一致");
        }

       if(!(Objects.equals(info.getPhone(), validateArgs.getPhone()) && Objects.equals(sent, validateArgs.getCode()))){
           throw new ValidateCodeNotMatchException("验证码不匹配");
       }

        repository.remove(validateArgs.getToken());
    }
}
