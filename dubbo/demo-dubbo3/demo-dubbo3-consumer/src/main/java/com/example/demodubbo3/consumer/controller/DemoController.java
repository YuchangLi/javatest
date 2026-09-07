package com.example.demodubbo3.consumer.controller;

import com.example.demodubbo3.dubbo.api.DemoProviderService;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @DubboReference(version = "1.0.0")
    private DemoProviderService demoProviderService;

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "world") String name) {
        return demoProviderService.sayHello(name);
    }
}