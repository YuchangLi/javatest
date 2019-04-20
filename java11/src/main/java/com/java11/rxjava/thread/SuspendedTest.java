package com.java11.rxjava.thread;

class MySuspendedThread implements Runnable{
  private int i = 0;
  public void run(){  
    synchronized (this) {
      System.out.println(Thread.currentThread().getName()+" run "+(++i));
//      Thread.currentThread().suspend();
      System.out.println(Thread.currentThread().getName()+" suspend over");
    }
  }
} 

public class SuspendedTest {
  @SuppressWarnings("deprecation")
  public static void main(String[] args) {
    Runnable run = new MySuspendedThread();
    Thread t = new Thread(run, "t1");
//    t.setDaemon(true);
    t.start();
    Thread t2 = new Thread(run, "t2");
    t2.start();
//    t.suspend();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t.resume();
    t2.resume();
    System.out.println("over");
  }
}
