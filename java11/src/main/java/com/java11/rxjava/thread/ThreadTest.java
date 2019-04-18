package com.java11.rxjava.thread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class MyThread implements Runnable{  
  private AtomicInteger ticket = new AtomicInteger(10000);  
//  private volatile int ticket = 10000;  
  public void run(){  
      for (int i = 0; i < 10; i++) {
//        ticket.decrementAndGet();        
        System.out.println(Thread.currentThread().getName() + ", ticket = " + ticket.getAndDecrement());
//        synchronized(this) {
//          System.out.println(Thread.currentThread().getName() + ", ticket = " + ticket--);
//        }
      }  
  }
  
  public int getTicket() {
    return ticket.intValue();
//    return ticket;
  }
  
} 

public class ThreadTest {
  public static void main(String[] args) throws FileNotFoundException, IOException{  
    MyThread my = new MyThread();  
    for (int i = 1; i <= 1000; i++) {
      new Thread(my, "thread"+i).start();
    }
    try {
      Thread.sleep(2000);
      System.out.println("ticket = "+my.getTicket());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }  
}
