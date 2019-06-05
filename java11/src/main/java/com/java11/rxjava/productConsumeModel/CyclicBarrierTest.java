package com.java11.rxjava.productConsumeModel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// 一组线程执行完后在执行主线程, 可复用
public class CyclicBarrierTest {
  public static void main(String[] args) {
    // 创建CyclicBarrier对象，
    // 并设置执行完一组5个线程的并发任务后，再执行MainTask任务
    CyclicBarrier cb = new CyclicBarrier(5, new MainTask());
    new SubTask("A", cb).start();
    new SubTask("B", cb).start();
    new SubTask("C", cb).start();
    new SubTask("D", cb).start();
    new SubTask("E", cb).start();
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    new SubTask("A1", cb).start();
    new SubTask("B2", cb).start();
    new SubTask("C3", cb).start();
    new SubTask("D4", cb).start();
    new SubTask("E5", cb).start();
    // 多或少都会使主线程blocking
//    new SubTask("F", cb).start();
  }
}


/**
 * 最后执行的任务
 */
class MainTask implements Runnable {
  public void run() {
    System.out.println("......终于要执行最后的任务了......");
  }
}


/**
 * 一组并发任务
 */
class SubTask extends Thread {
  private String name;
  private CyclicBarrier cb;

  SubTask(String name, CyclicBarrier cb) {
    this.name = name;
    this.cb = cb;
  }

  @Override
  public void run() {
    System.out.println("[并发任务" + name + "]  开始执行");
    // 模拟耗时的任务
//    for (int i = 0; i < 999999; i++); 
    try {
      Thread.sleep(100);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
    System.out.println("[并发任务" + name + "] is waiting on barrier，通知障碍器");
    try {
      // 每执行完一项任务就通知障碍器
      cb.await();
      System.out.println("[并发任务" + name + "] crossed the barrier");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    }
  }
}
