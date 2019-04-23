package com.java11.rxjava.productConsumeModel;

import java.util.concurrent.CountDownLatch;

// 一组线程执行完后在执行主线程，单次
public class CountDownLatchTest {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("main running start");
    CountDownLatch count = new CountDownLatch(2);
    Thread t1 = new Thread(()-> {
      System.out.println(Thread.currentThread().getName()+", doing start");
      try {
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+", doing end");
        count.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t1");
    t1.start();
    Thread t2 = new Thread(()-> {
      System.out.println(Thread.currentThread().getName()+", doing start");
      try {
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName()+", doing end");
        count.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t2");
    t2.start();
    Thread t3 = new Thread(()-> {
      System.out.println(Thread.currentThread().getName()+", doing start");
      try {
        count.await();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      try {
        Thread.sleep(300);
        System.out.println(Thread.currentThread().getName()+", doing end");
        count.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t3");
    t3.start();
//    count.await();
    System.out.println("main running end");
    Thread.sleep(3000);


    Thread t4 = new Thread(()-> {
      System.out.println(Thread.currentThread().getName()+", doing start");
      try {
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+", doing end");
        count.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t4");
    t4.start();
    
    Thread t5 = new Thread(()-> {
      System.out.println(Thread.currentThread().getName()+", doing start");
      try {
        count.await();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      try {
        Thread.sleep(300);
        System.out.println(Thread.currentThread().getName()+", doing end");
        count.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t5");
    t5.start();
  }
}
