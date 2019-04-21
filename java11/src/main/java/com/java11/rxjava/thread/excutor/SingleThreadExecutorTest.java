package com.java11.rxjava.thread.excutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleThreadExecutorTest {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
//    ExecutorService executor = Executors.newSingleThreadExecutor();
    ExecutorService executor = Executors.newFixedThreadPool(3);
    executor.execute(()->{System.out.println(Thread.currentThread().getId()+", test1s");try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }});
    executor.execute(()->{System.out.println(Thread.currentThread().getId()+", test2s");try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }});
    Future future = executor.submit(()->{System.out.println(Thread.currentThread().getId()+", test3s");});
    System.out.println(future.get());
  }
}
