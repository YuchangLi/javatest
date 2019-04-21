package com.java11.rxjava.thread.excutor;

import java.lang.System.Logger.Level;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Title: ExecutorUtil.java
 * @author liyuchang
 * @Description: 定时处理，延时处理
 * @date 2018年10月21日
 */
public class ExecutorUtil {
  private static final System.Logger logger = System.getLogger(ExecutorUtil.class.getName());
  //初始线程池数量
  private static final int MAX_THRAD_COUNT = 10;
  //初始线程池
  private static final ScheduledExecutorService excutor = Executors.newScheduledThreadPool(MAX_THRAD_COUNT);
  //按不同key键存储，达到取消目的 ScheduledFuture.cancel
  public static Map<String, ScheduledFuture<?>> tackMap = new ConcurrentHashMap<>();
  
  /**
   * @param command Runnable任务
   * @param delay 延迟处理时间
   * @param unit 延迟处理时间单位
   */
  public static ScheduledFuture<?> schedule(Runnable command,long delay, TimeUnit unit){
    return excutor.schedule(command, delay, unit);
  }

  /**
   * @param key 存放在map中的key
   * @param command Runnable任务
   * @param delay 延迟处理时间
   * @param unit 延迟处理时间单位
   * 会取消上次的定时任务
   */
  public static ScheduledFuture<?> schedule(String key, Runnable command,long delay, TimeUnit timeUnit){
    ScheduledFuture<?> task = tackMap.get(key);
    if(task!=null){
      task.cancel(true);
      // 从map里移除
      logger.log(Level.INFO, "从map里移除, key = {}", key);
      tackMap.remove(key);
    }
    // 正常执行完后从map里移除
    Runnable exCom = ()->{
      tackMap.remove(key);
      command.run();
    };
    task = schedule(exCom, delay, timeUnit);
    tackMap.put(key, task);
    return task;
  }
  
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    long t1 = System.currentTimeMillis();
    ScheduledFuture<?> future = schedule("test", ()->{System.out.println("ok "+(System.currentTimeMillis()-t1));}, 5, TimeUnit.SECONDS);
//    future.cancel(true);
    System.out.println(future.getClass());
    System.out.println(tackMap.size());
    System.out.println(future.isDone()+" "+future.getDelay(TimeUnit.SECONDS));
    Thread.sleep(6000);
    System.out.println(tackMap.size());
    System.out.println(future.isDone()+" "+future.getDelay(TimeUnit.SECONDS));
    System.out.println("future.get().getClass() = "+future.get().getClass());
//    ScheduledFuture<?> future2 = schedule("test", ()->{System.out.println("ok2 "+(System.currentTimeMillis()-t1));}, 5, TimeUnit.SECONDS);
//    System.out.println(tackMap.size());
//    System.out.println(future.isDone()+" "+future.getDelay(TimeUnit.SECONDS));
//    Thread.sleep(3000);
//    System.out.println(tackMap.size());
//    System.out.println(future.isDone()+" "+future.getDelay(TimeUnit.SECONDS));
//
//    Thread.sleep(5000);
//    System.out.println(tackMap.size());
//    Runnable r1 = ()->{System.out.println("r1");};
////    r1.run();
//   
//    Runnable r2 = ()->{
//      r1.run();
//      System.out.println("r2");
//      tackMap.remove("sdfdsf");
//      };
//    r2.run();
  }
}

