package com.csbaic.jd.config.application;

import com.csbaic.jd.service.goods.impl.GoodsHandlerConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by yjwfn on 2020/2/14.
 */
@Configuration
public class ApplicationConfiguration {


    @Bean
    @ConfigurationProperties("application")
    public ApplicationProperties applicationProperties(){
        return new ApplicationProperties();
    }



}
