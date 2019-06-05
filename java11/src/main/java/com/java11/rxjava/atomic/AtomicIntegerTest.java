package com.java11.rxjava.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
  
  private static AtomicInteger atomicInt = new AtomicInteger(100);
  
  public static void main(String[] args) throws InterruptedException {
      Thread intT1 = new Thread(new Runnable() {
          @Override
          public void run() {
              atomicInt.compareAndSet(100, 101);
              System.out.println("thread intT1:" + atomicInt.get());
              atomicInt.compareAndSet(101, 100);
              System.out.println("thread intT1:" + atomicInt.get());
          }
      });

      Thread intT2 = new Thread(new Runnable() {
          @Override
          public void run() {
              try {
                  TimeUnit.SECONDS.sleep(1);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              boolean c3 = atomicInt.compareAndSet(100, 101);
              System.out.println("thread intT2:" + atomicInt.get() + ",c3 is:" + c3);        //true
          }
      });

      intT1.start();
      intT2.start();
  }
}
