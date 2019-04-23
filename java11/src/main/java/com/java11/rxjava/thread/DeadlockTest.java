package com.java11.rxjava.thread;

class LockObj{
  private String objName;
  
  public String getObjName() {
    return objName;
  }

  public void setObjName(String objName) {
    this.objName = objName;
  }

  public LockObj(String objName) {
    super();
    this.objName = objName;
  }

  public synchronized void synlock(int seconds, LockObj o)  {
    System.out.println(Thread.currentThread().getName()+" get "+objName+" lock success, sleep "+seconds+" seconds");
    if(seconds>0) {
      try {
        Thread.sleep(seconds*1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    if(o!=null) {
      System.out.println(Thread.currentThread().getName()+" get "+objName+" lock success, sleep "+seconds+" seconds end , try to get "+o.getObjName()+" lock");
      o.synlock(0, null);
    }
  }
}

public class DeadlockTest {
  public static void main(String[] args) {
    LockObj o1 = new LockObj("o1");
    LockObj o2 = new LockObj("o2");
    Thread t1 = new Thread(()->{
      o1.synlock(1, o2);
    },"t1");
    t1.start();

    Thread t2 = new Thread(()->{
      o2.synlock(2, o1);
    },"t2");
    t2.start();
    
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
//      System.out.println("o1 try interrupt");
//      t1.interrupt();
//      t2.interrupt();
//      System.out.println("o1 interrupt success");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
