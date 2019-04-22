package com.java11.rxjava.thread.excutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsTest {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executor = Executors.newCachedThreadPool();
    Future<?> f = executor.submit(()->{
      System.out.println(Thread.currentThread().getName()+" in execute");
      int i = 1/0;
      return i;
    });
    System.out.println(f.get());
    executor.execute(()->{
      System.out.println(Thread.currentThread().getName()+" in execute");
    });
    executor.execute(()->{
      System.out.println(Thread.currentThread().getName()+" in execute");
    });
    executor.execute(()->{
      System.out.println(Thread.currentThread().getName()+" in execute");
    });
    executor.execute(()->{
      System.out.println(Thread.currentThread().getName()+" in execute");
    });
    executor.shutdown();
  }
}
