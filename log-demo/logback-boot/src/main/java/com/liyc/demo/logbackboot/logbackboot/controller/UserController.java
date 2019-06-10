package com.liyc.demo.logbackboot.logbackboot.controller;

import com.liyc.demo.logbackboot.logbackboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/getUser")
    public String getUser(){
        log.debug("UserController debug");
        log.info("UserController info");
        log.error("UserController error");
        return userService.getUser();
    }
}
