package com.java11.rxjava.thread;

import java.util.stream.Stream;

class TimeOrder{
  private volatile int value = 0;  

  public int get(){
    String str = null;
    str.equals("");
      return value;  
  }  
  public void set(int value){
    try {
      Thread.sleep(10);
      int i = 1/0;
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
      this.value = value;  
  }
}

public class TimeOrderTest {
public static void main(String[] args) {

  System.out.println(1 << 3);
  
//  TimeOrder to = new TimeOrder();
//  
//  Thread t1 = new Thread(()->{
//    to.set(3);
//  }, "t1");
//  t1.start();
//  
//  Thread t2 = new Thread(()->{
//    System.out.println(to.get());
//  }, "t2") ;
//  t2.start();
}
}
