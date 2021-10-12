package com.java.consumerdemo.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gmall-provider-demo")
public interface ProviderDemoClient {

    @GetMapping("/hello")
    public String hello();
}
