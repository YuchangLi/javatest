package org.example.threadlocaldemo;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {
    @Test
    public void threadLocalTest() {
       ThreadLocal<String> threadLocal = new ThreadLocal<>();
       threadLocal.set("value in main");
       new Thread(()->{
           String value = threadLocal.get();
           System.out.println(Thread.currentThread()+" value = " + value);
       }).start();
        String value = threadLocal.get();
        System.out.println(Thread.currentThread()+" value = " + value);
    }

    @Test
    public void inheritableThreadLocalTest() {
        ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set("value in main");
       new Thread(()->{
           String value = threadLocal.get();
           System.out.println(Thread.currentThread()+" value = " + value);
       }).start();
        String value = threadLocal.get();
        System.out.println(Thread.currentThread()+" value = " + value);
    }

    @Test
    public void threadPoolTest() throws InterruptedException {
        ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set("value in main");

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread()+" value = " + threadLocal.get());
            Runnable runnable = () -> System.out.println(Thread.currentThread() + " value = " + threadLocal.get());
            // 用ttl修饰
            executorService.execute(TtlRunnable.get(runnable));
            Thread.sleep(1000L);
            threadLocal.set("value in main "+i);
        }

    }

    @Test
    public void transmittableThreadLocalTest() throws InterruptedException {
        ThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();
        threadLocal.set("value in main");

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread()+" value = " + threadLocal.get());
            Runnable runnable = () -> System.out.println(Thread.currentThread() + " value = " + threadLocal.get());
            // 用ttl修饰
            executorService.execute(TtlRunnable.get(runnable));
            Thread.sleep(1000L);
            threadLocal.set("value in main "+i);
        }
    }

    @Test
    public void transmittableThreadLocalTest2() throws InterruptedException {
        ThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();
        threadLocal.set("value in main");

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService = TtlExecutors.getTtlExecutorService(executorService);
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread()+" value = " + threadLocal.get());
            Runnable runnable = () -> System.out.println(Thread.currentThread() + " value = " + threadLocal.get());
            // 用ttl修饰
            executorService.execute(runnable);
            Thread.sleep(1000L);
            threadLocal.set("value in main "+i);
        }
    }

    /**
     * 没有threadLocal 引用才会被回收
     */
    @Test
    public void threadLocalKeyWeakReTest() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        System.out.println(Thread.currentThread()+" value = " + threadLocal.get());
        threadLocal.set("value in main");
        System.out.println(Thread.currentThread()+" value = " + threadLocal.get());
        System.gc();
        System.gc();
        System.gc();
        System.out.println(Thread.currentThread()+" value = " + threadLocal.get());
    }
}
