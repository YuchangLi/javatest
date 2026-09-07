package com.example.demodubbo3.dubbo.service;

import com.example.demodubbo3.dubbo.api.DemoProviderService;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService(version = "1.0.0", weight = 900)
@Service("demoProviderService")
public class DemoProviderServiceImpl implements DemoProviderService {

    @Override
    public String sayHello(String name) {
        System.out.println("provider1 received: " + name);
        return "Hello1 " + name;
    }
}