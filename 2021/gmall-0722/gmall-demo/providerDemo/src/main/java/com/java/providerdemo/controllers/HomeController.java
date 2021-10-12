package com.java.providerdemo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RefreshScope
public class HomeController {

    @Value("${welcome}")
    private String welcome;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${redis.url}")
    private String redisUrl;

    @GetMapping("/hello")
    public String hello() {
        return "Hello " + welcome + ":" + databaseUrl + ":" + redisUrl;
    }
}
