package com.example.demodubbo3.consumer.service;

import com.example.demodubbo3.dubbo.api.DemoProviderService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService(version = "2.0.0")
@Service
public class DemoProviderServiceImpl2 implements DemoProviderService {

    public String sayHello(String name) {
        System.out.println("DemoProviderServiceImpl2.sayHello");
        return "Hello2 " + name;
    }
}