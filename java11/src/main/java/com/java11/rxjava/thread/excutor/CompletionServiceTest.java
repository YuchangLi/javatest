package com.java11.rxjava.thread.excutor;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: CompletionServiceTest
 * @Description: 实现了CompletionService，将执行完成的任务放到阻塞队列中，通过take或poll方法来获得执行结果 例4：（启动10条线程，谁先执行完成就返回谁）
 * @author liyuchang
 * @date 2019年4月21日
 */
public class CompletionServiceTest {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executor = Executors.newFixedThreadPool(10);        //创建含10.条线程的线程池
    CompletionService completionService = new ExecutorCompletionService(executor);
    for (int i =1; i <=10; i ++) {
      final  int result = i;
      completionService.submit(new Callable() {
            public Object call() throws Exception {
              System.out.println(Thread.currentThread().getName()+" in call");
                Thread.sleep(new Random().nextInt(5000));   //让当前线程随机休眠一段时间
                return result;
            }
        });
    }
    for (int i =1; i <=10; i ++) {
      System.out.println(completionService.take().get()); //获取执行结果
    }
    System.out.println("in main");   //获取执行结果
    executor.shutdown();
  }
}
