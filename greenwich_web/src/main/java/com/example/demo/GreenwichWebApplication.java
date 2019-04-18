package com.example.demo;

import java.lang.System.Logger.Level;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GreenwichWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenwichWebApplication.class, args);
	}

}

@RestController
@RequestMapping("/user")
class UserController{
  System.Logger logger = System.getLogger(getClass().getName()); 
  @RequestMapping(value = "/getUser", method = RequestMethod.GET)
  public String getUser(HttpServletRequest request) {
    logger.log(Level.INFO, "Thread=["+Thread.currentThread().getId()+", "+Thread.currentThread().getName()+"], ip="+request.getRemoteAddr()+" host="+request.getRemoteHost()+" , ruser="+request.getRemoteUser());
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if(Math.random()>0.5) {
//      int i = 1/0;
    }
    return "Hello World";
  }
}