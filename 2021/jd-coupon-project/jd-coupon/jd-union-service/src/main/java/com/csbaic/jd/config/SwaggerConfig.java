package com.csbaic.jd.config;

import com.csbaic.jd.config.application.ApplicationProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private ApplicationProperties applicationProperties;



    @Bean
    public Docket createAdminRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfo())
                .host(applicationProperties.getApiHost())
//                .groupName("user-com.csbaic.rbac.dto.service")
                .groupName("Admin")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.csbaic.jd.controller.admin"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfo())
                .host(applicationProperties.getApiHost())
//                .groupName("user-com.csbaic.rbac.dto.service")
                .groupName("App")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.csbaic.jd.controller.app"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }


    @Bean
    SecurityScheme securityScheme() {
        return new ApiKey("bearer", "Authorization", "header");
    }

    @Bean
    SecurityContext securityContext() {
        SecurityReference securityReference = SecurityReference.builder()
                .reference("bearer")
                .scopes(new AuthorizationScope[]{})
                .build();

        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(securityReference))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("京东联盟社交电商")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
