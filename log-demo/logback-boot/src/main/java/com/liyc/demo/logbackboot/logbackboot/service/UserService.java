package com.liyc.demo.logbackboot.logbackboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    public String getUser() {
        log.debug("UserService debug");
        log.info("UserService info");
        log.error("UserService error");
        return "user";
    }
}
