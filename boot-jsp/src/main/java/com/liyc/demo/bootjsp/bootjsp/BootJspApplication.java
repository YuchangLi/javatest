package com.liyc.demo.bootjsp.bootjsp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@Slf4j
public class BootJspApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootJspApplication.class, args);
    }

    @Controller
    @RequestMapping("/index")
    class IndexController {

        @GetMapping("/")
        public String   index(Model model) {
            for (EnumShareChannel ch : EnumShareChannel.values()){
                System.out.println(ch);
            }

            log.info("index jsp1");
            model.addAttribute("name", "lyc");
            model.addAttribute("shareChannels", EnumShareChannel.values());
//            ModelAndView view = new ModelAndView("index");
//            view.addObject("pwd", "111");
            return "index";
        }
    }
}
