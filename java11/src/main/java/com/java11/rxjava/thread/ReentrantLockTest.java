package com.java11.rxjava.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
  public static void main(String[] args) throws InterruptedException {
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    System.out.println("t1 step 1");
    lock.lock();
    condition.await();
    lock.unlock();
    condition.signalAll();
    System.out.println("t1 step 2");
    System.out.println("t1 step 3");
    if(true) return ;
//    System.out.println("t1 lock 5 seconds");
    Thread t1 = new Thread(()-> {
      System.out.println("t1 step 1");
      System.out.println("t1 step 2");
      System.out.println("t1 step 3");
      lock.lock();
      try {
        System.out.println("t1 await....");
        condition.await();
        System.out.println("t1 await over....");
      } catch (InterruptedException e2) {
        e2.printStackTrace();
      }
      System.out.println("t1 lock 5 seconds");
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
      try {
      } catch (Exception e) {
        e.printStackTrace();
      }finally {
        lock.unlock();
      }
    }, "t1");
    t1.start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
    Thread t2 = new Thread(()-> {
      System.out.println("t2 step 1");
      System.out.println("t2 step 2");
      lock.lock();
      try {
        System.out.println("t2 step 3");
      } catch (Exception e) {
        e.printStackTrace();
      }finally {
        condition.signalAll();
        lock.unlock();
//        condition.signalAll();
      }
    }, "t2");
    t2.start();
  }
}
