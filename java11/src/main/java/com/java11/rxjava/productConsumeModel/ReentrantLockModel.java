package com.java11.rxjava.productConsumeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


class BufferArea{
  int maxSize = 5;
  List<String> buffer = new ArrayList<>(maxSize);
  ReentrantLock lock = new ReentrantLock();
  Condition condition = lock.newCondition();
  
  int product(String str) {
    if(buffer.size() == maxSize) {
      System.out.println(Thread.currentThread().getName()+" blocked, wait consumer consume!, buffer.size = "+buffer.size());
      try {
        lock.lock();
        condition.await();
        System.out.println(Thread.currentThread().getName()+" blocked over, buffer.size = "+buffer.size());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }finally {
        lock.unlock();
      }
    }
    buffer.add(str);
    System.out.println(Thread.currentThread().getName()+" product success, buffer.size = "+buffer.size());
    lock.lock();
    condition.signalAll();
    lock.unlock();
    return buffer.size();
  }
  
  synchronized int consume() {
    if(buffer.size()==0) {
      System.out.println(Thread.currentThread().getName()+" blocked, wait product prduct!, buffer.size = "+buffer.size());
      try {
        lock.lock();
        condition.await();
        System.out.println(Thread.currentThread().getName()+" blocked over, buffer.size = "+buffer.size());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }finally {
        lock.unlock();
      }
    }
    buffer.remove(0);
    System.out.println(Thread.currentThread().getName()+" consume success, buffer.size = "+buffer.size());
    lock.lock();
    try {
      condition.signalAll();
    } catch (Exception e) {
      e.printStackTrace();
    }finally {
      lock.unlock();
    }
    return buffer.size();
  }

  public int getSize() {
    return buffer.size();
  }
}
  
public class ReentrantLockModel {
  public static void main(String[] args) {
    BufferArea buffer = new BufferArea();
    Thread product = new Thread(()-> {
     while(true) {
       buffer.product("str");
       try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
     }
    }, "prodcuter") ;
    product.start();
    try {
      Thread.sleep(100);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
    Thread consumer = new Thread(()-> {
      while(true) {
        buffer.consume();
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "consumer") ;
    consumer.start();
  }
}
