package org.springboot.dubbo.consumer;

import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.dubbo.api.service.DemoService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDubboConsumerApplicationTests {

  @Reference(version = "${demo.service.version}")
  private DemoService demoService;

  @Test
  public void demoServiceTest() {
    System.out.println(this.demoService.sayHello("李玉长！"));
  }

}
