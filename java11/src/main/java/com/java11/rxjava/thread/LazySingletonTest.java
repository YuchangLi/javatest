package com.java11.rxjava.thread;

class LazySingleton {
  private int someField;  

  private static LazySingleton instance;  

  private LazySingleton() {
    System.out.println(Thread.currentThread().getName()+", "+"in LazySingleton()");
    if(Thread.currentThread().getName().equals("t1")) {
      System.out.println(Thread.currentThread().getName()+", "+" sleep 3000");
      try {
//        Thread.sleep(3000);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    this.someField = 1;         // (1)  
  }  

  public static LazySingleton getInstance() {
      if(Thread.currentThread().getName().equals("t2")) {
        try {
          System.out.println(Thread.currentThread().getName()+" sleep 300");
          Thread.sleep(300);
          System.out.println(Thread.currentThread().getName()+" sleep 300 over instance == "+ (instance == null));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      if (instance == null) {// (2)  
        System.out.println(Thread.currentThread().getName()+" into synchronized");
          synchronized(LazySingletonTest.class) {               // (3)  
              if (instance == null) {    // (4)  
                  instance = new LazySingleton();           // (5)  
              }  
          }  
      }  
      return instance;                                      // (6)  
  }  

  public int getSomeField() {  
      return this.someField;                                // (7)  
  }  
}

public class LazySingletonTest {
  public static void main(String[] args) {
    new Thread(()->{
      System.out.println(Thread.currentThread().getName()+", "+LazySingleton.getInstance().getSomeField());
    }, "t1").start();
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(()->{
      System.out.println(Thread.currentThread().getName()+", "+LazySingleton.getInstance().getSomeField());
    }, "t2").start();
  }
}