package com.java.consumerdemo.controllers;

import com.java.consumerdemo.feigns.ProviderDemoClient;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @Value("${myApplicationName}")
    private String myName;

    @Autowired
    ProviderDemoClient providerDemoClient;

    @GetMapping("/hello")
    public String hello() {
        return "Hello " + providerDemoClient.hello();
    }
}
