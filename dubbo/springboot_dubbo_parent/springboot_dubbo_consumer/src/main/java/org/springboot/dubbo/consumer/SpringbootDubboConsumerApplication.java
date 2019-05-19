package org.springboot.dubbo.consumer;

import org.apache.dubbo.config.annotation.Reference;
import org.springboot.dubbo.api.service.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@RequestMapping("/user")
public class SpringbootDubboConsumerApplication {
    @Reference(version = "${demo.service.version}")
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDubboConsumerApplication.class);
    }

    @GetMapping("/name")
    public String name(String name) {
        return this.demoService.sayHello(name);
    }
}
