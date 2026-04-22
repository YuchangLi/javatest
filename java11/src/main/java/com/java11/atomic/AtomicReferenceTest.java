package com.java11.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName: AtomicReferenceTest
 * @Description: AtomicReference基本用法和多线程测试
 * @author liyuchang
 * @date 2026年4月22日
 */
public class AtomicReferenceTest {
  
  // 创建AtomicReference实例，初始值为"Initial Value"
  private static AtomicReference<String> atomicRef = new AtomicReference<>();
  
  public static void main(String[] args) {
    // 初始设置
    atomicRef.set("Initial Value");
    log("Initial value: " + atomicRef.get());
    
    // 测试set和get方法
    atomicRef.set("New Value");
    log("After set: " + atomicRef.get());
    
    // 测试compareAndSet方法
    boolean success1 = atomicRef.compareAndSet("New Value", "Updated Value");
    log("CAS success: " + success1 + ", Value: " + atomicRef.get());
    
    // 测试失败的CAS操作
    boolean success2 = atomicRef.compareAndSet("Wrong Value", "This won't update");
    log("CAS success: " + success2 + ", Value: " + atomicRef.get());
    
    // 多线程测试
    log("\n--- Multi-thread Test ---");
    
    // 创建多个线程同时尝试更新值
    for (int i = 0; i < 5; i++) {
      final int threadId = i;
      new Thread(() -> {
        log("Thread " + threadId + " starting, current value: " + atomicRef.get());
        
        // 尝试将值更新为当前线程的标识
        String newValue = "Updated by Thread " + threadId;
        boolean success = atomicRef.compareAndSet(atomicRef.get(), newValue);
        
        if (success) {
          log("Thread " + threadId + " successfully updated value to: " + newValue);
        } else {
          log("Thread " + threadId + " failed to update, value was changed by another thread");
        }
        
        try {
          // 短暂休眠，让其他线程有机会执行
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }).start();
    }
    
    // 测试AtomicReference的非阻塞特性
    log("\n--- Non-blocking Test ---");
    
    // 使用AtomicReference实现一个简单的非阻塞计数器
    AtomicReference<Integer> counter = new AtomicReference<>(0);
    
    for (int i = 0; i < 3; i++) {
      final int threadId = i;
      new Thread(() -> {
        for (int j = 0; j < 5; j++) {
          // 使用循环CAS实现非阻塞更新
          Integer current;
          Integer next;
          do {
            current = counter.get();
            next = current + 1;
          } while (!counter.compareAndSet(current, next));
          
          log("Counter updated by Thread " + threadId + " to: " + next);
          
          try {
            TimeUnit.MILLISECONDS.sleep(50);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }).start();
    }
  }
  
  private static void log(String logString) {
    System.out.println(logString);
  }
}