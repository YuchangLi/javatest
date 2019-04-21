package com.java11.rxjava.thread;

public class ThreadException {
  public static void main(String[] args) {
    Thread t = new Thread(()-> {
      System.out.println("int thread");
      int i = 1/0;
    });
    t.setUncaughtExceptionHandler((Thread t1, Throwable e1)->{
      System.out.println(t == t1);
      e1.printStackTrace();
    });
     t.start();
  }
}
