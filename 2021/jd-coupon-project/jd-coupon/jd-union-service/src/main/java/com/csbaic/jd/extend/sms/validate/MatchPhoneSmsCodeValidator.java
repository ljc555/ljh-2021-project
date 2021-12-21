package com.csbaic.jd.extend.sms.validate;

import com.csbaic.jd.extend.sms.exception.SmsValidateException;
import com.csbaic.jd.extend.sms.exception.ValidateCodeNotMatchException;
import com.csbaic.jd.extend.sms.repository.SmsInfo;
import com.csbaic.jd.extend.sms.repository.SmsRepository;
import com.google.common.base.Strings;

import java.util.Map;
import java.util.Objects;

public class MatchPhoneSmsCodeValidator implements SmsCodeValidator {

    /**
     * 短信存在库
     */
    private final SmsRepository repository;

    public MatchPhoneSmsCodeValidator(SmsRepository repository) {
        this.repository = repository;
    }


    @Override
    public void validate(ValidateArgs validateArgs) throws SmsValidateException {

        Map<String, SmsInfo> smsInfoMap = repository.getByPhone(validateArgs.getPhoneExt(), validateArgs.getPhone());

        if (smsInfoMap == null || smsInfoMap.isEmpty()) {
            throw new SmsValidateException("短信未找到或已过期");
        }


        Map.Entry<String, SmsInfo> smsInfoEntry = smsInfoMap.entrySet().stream().findFirst().orElse(null);
        SmsInfo info = smsInfoEntry.getValue();
        String sent = info.getCode();

        if (Strings.isNullOrEmpty(sent)) {
            throw new SmsValidateException("短信验证码未找到");
        }

        if (!Objects.equals(info.getBizId(), validateArgs.getBizId())) {
            throw new SmsValidateException("业务Id不一致");
        }

        if (!(Objects.equals(info.getPhone(), validateArgs.getPhone()) && Objects.equals(sent, validateArgs.getCode()))) {
            throw new ValidateCodeNotMatchException("验证码不匹配");
        }

        repository.remove(smsInfoEntry.getKey());
    }
}
