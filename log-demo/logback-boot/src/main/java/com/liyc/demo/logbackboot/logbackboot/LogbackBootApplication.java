package com.liyc.demo.logbackboot.logbackboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LogbackBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogbackBootApplication.class, args);
        log.debug("LogbackBootApplication debug");
        log.info("LogbackBootApplication info");
        log.error("LogbackBootApplication error");
    }

}
