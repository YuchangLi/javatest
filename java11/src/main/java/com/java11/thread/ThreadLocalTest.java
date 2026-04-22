package com.java11.thread;

/**
 * @ClassName: ThreadLocalTest
 * @Description: ThreadLocal使用演示
 * @author liyuchang
 * @date 2026年4月22日
 */
public class ThreadLocalTest {
    
    /**
     * 基本ThreadLocal变量
     */
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    
    /**
     * 带有初始值的ThreadLocal变量
     */
    private static ThreadLocal<String> initialThreadLocal = ThreadLocal.withInitial(() -> "Default Value");
    
    /**
     * 演示ThreadLocal线程隔离性的内部类
     */
    static class ThreadLocalRunnable implements Runnable {
        private int threadId;
        
        public ThreadLocalRunnable(int threadId) {
            this.threadId = threadId;
        }
        
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " - Initial value: " + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + " - InitialThreadLocal value: " + initialThreadLocal.get());
            
            // 设置当前线程的ThreadLocal值
            threadLocal.set(threadId * 100);
            initialThreadLocal.set("Value from Thread " + threadId);
            
            System.out.println(Thread.currentThread().getName() + " - After set: threadLocal = " + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + " - After set: initialThreadLocal = " + initialThreadLocal.get());
            
            // 模拟线程执行一些任务
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // 再次获取值，验证线程隔离
            System.out.println(Thread.currentThread().getName() + " - Before remove: threadLocal = " + threadLocal.get());
            
            // 清理ThreadLocal资源，避免内存泄漏
            threadLocal.remove();
            initialThreadLocal.remove();
            
            System.out.println(Thread.currentThread().getName() + " - After remove: threadLocal = " + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + " - After remove: initialThreadLocal = " + initialThreadLocal.get());
        }
    }
    
    /**
     * 演示不清理ThreadLocal可能导致的内存泄漏
     */
    static class MemoryLeakDemo implements Runnable {
        @Override
        public void run() {
            // 创建一个大对象放入ThreadLocal
            byte[] largeObject = new byte[1024 * 1024]; // 1MB
            ThreadLocal<byte[]> leakyThreadLocal = new ThreadLocal<>();
            leakyThreadLocal.set(largeObject);
            
            System.out.println(Thread.currentThread().getName() + " - Created large object in ThreadLocal");
            
            // 不调用remove()，模拟内存泄漏情况
            // leakyThreadLocal.remove();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== ThreadLocal Basic Usage Test ===\n");
        
        // 创建多个线程测试ThreadLocal的线程隔离性
        for (int i = 1; i <= 3; i++) {
            Thread thread = new Thread(new ThreadLocalRunnable(i), "Thread-" + i);
            thread.start();
        }
        
        // 等待所有线程执行完毕
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== Memory Leak Demo ===\n");
        
        // 演示内存泄漏问题
        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(new MemoryLeakDemo(), "Leak-Thread-" + i);
            thread.start();
        }
        
        System.out.println("\n=== Test Completed ===");
    }
}