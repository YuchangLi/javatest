package com.liyuchang.dubbo.rest;

import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@EnableDubbo(scanBasePackages = "com.liyuchang.dubbo.rest")
@PropertySource("classpath:/spring/dubbo-rest.properties")
public class DubboRestApplication {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DubboRestApplication.class);
        context.start();
        System.in.read();
    }

}
