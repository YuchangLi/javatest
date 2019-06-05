package com.liyuchang.dubbo.rest.swagger;

import io.swagger.jaxrs.config.BeanConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerBeanConfig {
    @Bean
    public BeanConfig beanConfig(){
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/services");
        beanConfig.setTitle("title");
        beanConfig.setDescription("desc");
        beanConfig.setContact("abc");
        beanConfig.setLicense("Apache 2.0");
        beanConfig.setResourcePackage("com.liyuchang.dubbo.rest.service.impl");
        beanConfig.setVersion("2.0");
        beanConfig.setScan(true);
        return beanConfig;
    }
}
