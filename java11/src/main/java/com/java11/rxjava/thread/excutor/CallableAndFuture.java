package com.java11.rxjava.thread.excutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
      ExecutorService executor = Executors.newSingleThreadExecutor();
      Future<String> future = executor.submit(new Callable<String>() {   //接受一上callable实例
          public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()+", in call, sleep 1 seconds");
            Thread.sleep(2000);
            int i = 1/0;
             return "MOBIN";
          }
      });
      System.out.println("任务的执行结果："+future.get());
      System.out.println("in main");   //获取执行结果
  }
}