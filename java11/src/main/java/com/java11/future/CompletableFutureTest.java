package com.java11.future;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {
    public static void main(String[] args) {
        allOfTest();
    }

    private static void allOfTest() {
        // T1
        CompletableFuture<String> futureT1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() +", T1 is executing. Current time：" + DateUtil.now());
            // 模拟耗时操作
            ThreadUtil.sleep(1000);
            return "T1";
        });
// T2
        CompletableFuture<Void> futureT2 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() +", T2 is executing. Current time：" + DateUtil.now());
            ThreadUtil.sleep(3000);
        });

// 使用allOf()方法合并T1和T2的CompletableFuture，等待它们都完成
        CompletableFuture<Void> bothCompleted = CompletableFuture.allOf(futureT1, futureT2);
// 当T1和T2都完成后，执行T3
        CompletableFuture<Void> completableFuture = bothCompleted.thenRun(() -> {
            System.out.println(Thread.currentThread().getName() + ", T3 is executing after T1 and T2 have completed.Current time：" + DateUtil.now());
            System.out.println("futureT1.join() = " + futureT1.join());
            System.out.println("futureT2.join() = " + futureT2.join());
        });
// 等待所有任务完成，验证效果, join()方法会阻塞，直到所有任务完成
        completableFuture.join();
        System.out.println("main thread start sleep. Current time：" + DateUtil.now());
        ThreadUtil.sleep(5000);

        System.out.println("All tasks completed. Current time：" + DateUtil.now());
    }
}
