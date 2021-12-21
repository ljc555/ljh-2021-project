package com.csbaic.jd.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.extend.sms.validate.ValidateArgs;
import com.csbaic.jd.service.sms.SmsBiz;
import com.csbaic.jd.service.sms.SmsService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
@Api(value = "短信", tags = "短信")
@ResponseResult
public class SmsController {

    @Autowired
    private SmsService smsService;

    @ApiOperation("发送验证手机号验证码")
    @PostMapping("/send_validate_phone_code")
    public void sendValidatePhoneCode(@RequestBody PhoneNumber phoneNumber){
         smsService.send(phoneNumber.getPhoneExt(), phoneNumber.getPhone(), SmsBiz.VALIDATE_PHONE);
    }
    @ApiOperation("验证手机号验证码")
    @PostMapping("/validate_phone")
    public void validatePhoneCode(@RequestBody ValidatePhoneNumber phoneNumber){
        smsService.validate(new ValidateArgs(phoneNumber.getPhoneExt(), phoneNumber.getPhone(), phoneNumber.getCode(), SmsBiz.VALIDATE_PHONE.name()));
    }

}
