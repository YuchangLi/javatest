package com.java11.rxjava.thread.excutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(2);
//    BlockingQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
    ThreadFactory threadFactory = Executors.defaultThreadFactory();
    // AbortPolicy：异常拒绝；DiscardPolicy：不处理；DiscardOldestPolicy：从queue里移除最早的；CallerRunsPolicy：直接跳run方法
    RejectedExecutionHandler handler = new AbortPolicy(); 
//    RejectedExecutionHandler handler = new DiscardPolicy(); 
//    RejectedExecutionHandler handler = new DiscardOldestPolicy(); // task会被移除queue，8加入执行
//    RejectedExecutionHandler handler = new CallerRunsPolicy();
    ExecutorService executor = new ThreadPoolExecutor(3, 5, 0, TimeUnit.SECONDS, workQueue, threadFactory, handler);
    try {
      // use new thread <= coresize
      executor.execute(new Task(1));
      executor.execute(new Task(2));
      executor.execute(new Task(3));
      // 4 and 5 add to queue 
      executor.execute(new Task(4));
      executor.execute(new Task(5));
      // user new thread <= maxsize
      executor.execute(new Task(6));
      executor.execute(new Task(7));
      // do RejectedExecution
      executor.execute(new Task(8));
    } catch (Exception e) {
      System.out.println("e = " + e.getMessage());
      e.printStackTrace();
    }
    finally {
      executor.shutdown();
    }
  }
}

class Task implements Runnable {
  int i;
  
  @Override
  public String toString() {
    return "Task [i=" + i + "]";
  }

  public Task(int i) {
    super();
    this.i = i;
  }

  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName()+" task "+i+" do stard");
    try {
      Thread.sleep(3000);
      System.out.println(Thread.currentThread().getName()+" task "+i+" do end");
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}
