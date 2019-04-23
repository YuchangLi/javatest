package com.example.demo;

import java.lang.System.Logger.Level;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@RestController
@Configuration
public class GreenwichWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(GreenwichWebApplication.class, args);
  }

//  @Bean
//  public ServletListenerRegistrationBean<RequestContextListener> listenerRegistration3() {
//      return new ServletListenerRegistrationBean<>(new RequestContextListener());
//  }
}

@Component
//@Scope(value = WebApplicationContext.SCOPE_REQUEST)
class UserService {
  String getUserName() {
    return "username";
  }
}

@RestController
@RequestMapping("/user")
class UserController {
  System.Logger logger = System.getLogger(getClass().getName());
  
  @Autowired
  UserService userService;
  
  @RequestMapping(value = "/getUser", method = RequestMethod.GET)
  public String getUser(HttpServletRequest request) {
    System.out.println(Thread.currentThread().getId()+", userService = "+userService);
    logger.log(Level.INFO,
        "Thread=[" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName()
        + "], ip=" + request.getRemoteAddr() + " host=" + request.getRemoteHost() + " , ruser="
        + request.getRemoteUser());
    // return "Hello World";
    return userService.getUserName();
  }
}


