package com.java11.rxjava.productConsumeModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// 信号并发下只有抢到信号的线程才能执行
public class SemaphoreTest {
  public static void main(String[] args) {
    // 采用新特性来启动和管理线程——内部使用线程池
    ExecutorService exec = Executors.newCachedThreadPool();
    // 只允许5个线程同时访问
    final Semaphore semp = new Semaphore(2);
    // 模拟10个客户端访问
    for (int index = 0; index < 3; index++) {
      final int num = index;
      Runnable run = new Runnable() {
        public void run() {
          try {
            // 获取许可
            System.out.println("线程" + Thread.currentThread().getName() + "尝试获得许可：" + num);
            semp.acquire();
            System.out.println("线程" + Thread.currentThread().getName() + "获得许可成功：" + num);
            // 模拟耗时的任务
            // for (int i = 0; i < 999999; i++) ;
            try {
              Thread.sleep(3000);
            } catch (InterruptedException e1) {
              e1.printStackTrace();
            }
            // 释放许可
            System.out.println("线程" + Thread.currentThread().getName() + "释放许可：" + num);
            semp.release();
            System.out.println("当前允许进入的任务个数：" + semp.availablePermits());
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      };
      exec.execute(run);
    }
    // 关闭线程池
    exec.shutdown();
  }
}
