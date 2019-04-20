package com.java11.rxjava.thread;

class TimeOrder{
  private volatile int value = 0;  

  public int get(){  
      return value;  
  }  
  public void set(int value){
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
      this.value = value;  
  }
}

public class TimeOrderTest {
public static void main(String[] args) {
  TimeOrder to = new TimeOrder();
  
  Thread t1 = new Thread(()->{
    to.set(3);
  }, "t1");
  t1.start();
  
  Thread t2 = new Thread(()->{
    System.out.println(to.get());
  }, "t2") ;
  t2.start();
}
}
