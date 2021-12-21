package com.csbaic.jd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
@MapperScan("com.csbaic.jd.mapper")
public class JdSocialApplication
{
    public static void main(String[] args){
        SpringApplication.run(JdSocialApplication.class);
    }








}
