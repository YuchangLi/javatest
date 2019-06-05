package com.springboot.dubbo.provider.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springboot.dubbo.api.service.DemoService;
import org.springframework.beans.factory.annotation.Value;

@Service(version = "1.0.0")
public class DefaultDemoService implements DemoService {

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }
}
