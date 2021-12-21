package com.csbaic.jd.extend.sms;

import com.csbaic.jd.config.application.AliSms;
import com.csbaic.jd.config.application.ApplicationProperties;
import com.csbaic.jd.extend.sms.ali.AliSmsConfig;
import com.csbaic.jd.extend.sms.ali.AliyunSmsClient;
import com.csbaic.jd.extend.sms.client.NoOpSmsClient;
import com.csbaic.jd.extend.sms.client.SmsClient;
import com.csbaic.jd.extend.sms.gen.RandomValidateCodeGenerator;
import com.csbaic.jd.extend.sms.gen.SmsValidateCodeGenerator;
import com.csbaic.jd.extend.sms.repository.GuavaCacheRepository;
import com.csbaic.jd.extend.sms.repository.SmsRepository;
import com.csbaic.jd.extend.sms.validate.MatchPhoneSmsCodeValidator;
import com.csbaic.jd.extend.sms.validate.SmsCodeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfiguration  {


    @ConditionalOnMissingBean(SmsClient.class)
    @Bean
    public SmsClient smsClient(ApplicationProperties properties){
        AliSms aliSms = properties.getSms();
        return new AliyunSmsClient(new AliSmsConfig(aliSms.getAccessKeyId(), aliSms.getAccessSecret(), aliSms.getSignName(), aliSms.getTemplateCode()));
    }




    @ConditionalOnMissingBean(SmsValidateCodeGenerator.class)
    @Bean
    public SmsValidateCodeGenerator generator(){
        return new RandomValidateCodeGenerator();
    }


    @ConditionalOnMissingBean(SmsRepository.class)
    @Bean
    public SmsRepository smsRepository(){
        return new GuavaCacheRepository();
    }

    @ConditionalOnMissingBean(SmsCodeValidator.class)
    @Bean
    public SmsCodeValidator smsCodeValidator(SmsRepository repository){
        return new MatchPhoneSmsCodeValidator(repository);
    }

}
