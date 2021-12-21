package com.csbaic.jd.service.sms;

import com.csbaic.jd.dto.SmsSentResult;
import com.csbaic.jd.extend.sms.validate.ValidateArgs;

public interface SmsService {

    /**
     * 发送验证码
     * @param phoneExt 手机区号
     * @param phone 手机号
     * @param bizId 业务id
     */
    SmsSentResult send(String phoneExt, String phone, SmsBiz bizId);

    /**
     * 验证短信
     */
    void validate(ValidateArgs args);

}
