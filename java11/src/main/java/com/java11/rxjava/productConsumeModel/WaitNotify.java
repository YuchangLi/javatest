package com.java11.rxjava.productConsumeModel;

import java.util.ArrayList;
import java.util.List;

class Buffer{
  int maxSize = 5;
  List<String> buffer = new ArrayList<>(maxSize);
  
  synchronized int product(String str) {
    if(buffer.size() == maxSize) {
      System.out.println(Thread.currentThread().getName()+" blocked, wait consumer consume!, buffer.size = "+buffer.size());
      try {
        this.wait();
        System.out.println(Thread.currentThread().getName()+" blocked over, buffer.size = "+buffer.size());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    buffer.add(str);
    System.out.println(Thread.currentThread().getName()+" product success, buffer.size = "+buffer.size());
    this.notifyAll();
    return buffer.size();
  }
  
  synchronized int consume() {
    if(buffer.size()==0) {
      System.out.println(Thread.currentThread().getName()+" blocked, wait product prduct!, buffer.size = "+buffer.size());
      try {
        this.wait();
        System.out.println(Thread.currentThread().getName()+" blocked over, buffer.size = "+buffer.size());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    buffer.remove(0);
    System.out.println(Thread.currentThread().getName()+" consume success, buffer.size = "+buffer.size());
    this.notifyAll();
    return buffer.size();
  }
}

public class WaitNotify {
  public static void main(String[] args) {
    Buffer buffer = new Buffer();
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
