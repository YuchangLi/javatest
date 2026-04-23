package com.java11.future;

import java.util.concurrent.*;

public class FutureTest {

    public static void main(String[] args) throws Exception {
        System.out.println("========== 测试1: 基本 Future 使用 ==========\n");
        testBasicFuture();

        System.out.println("\n========== 测试2: Future 超时控制 ==========\n");
        testFutureTimeout();

        System.out.println("\n========== 测试3: Future 异常处理 ==========\n");
        testFutureException();

        System.out.println("\n========== 测试4: CompletableFuture 链式调用 ==========\n");
        testCompletableFutureChain();

        System.out.println("\n========== 测试5: CompletableFuture 组合 ==========\n");
        testCompletableFutureCombine();
    }

    /**
     * 基本 Future 使用
     */
    private static void testBasicFuture() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        Future<String> future = executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + ": 开始执行任务...");
            Thread.sleep(1000);
            return "Hello Future!";
        });
        
        System.out.println("主线程继续执行...");
        String result = future.get();
        System.out.println("获取到结果: " + result);
        
        executor.shutdown();
    }

    /**
     * Future 超时控制
     */
    private static void testFutureTimeout() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        Future<String> future = executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + ": 开始执行耗时任务...");
            Thread.sleep(3000);
            return "耗时任务完成";
        });
        
        try {
            System.out.println("尝试获取结果(超时时间2秒)...");
            String result = future.get(2, TimeUnit.SECONDS);
            System.out.println("获取到结果: " + result);
        } catch (TimeoutException e) {
            System.out.println("⚠️ 任务超时: " + e.getMessage());
            future.cancel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
    }

    /**
     * Future 异常处理
     */
    private static void testFutureException() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        Future<Integer> future = executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + ": 执行任务...");
            Thread.sleep(500);
            return 10 / 0;
        });
        
        try {
            Integer result = future.get();
            System.out.println("结果: " + result);
        } catch (ExecutionException e) {
            System.out.println("⚠️ 任务执行异常: " + e.getCause().getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
    }

    /**
     * CompletableFuture 链式调用
     */
    private static void testCompletableFutureChain() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤1: 异步任务开始");
            return "Hello";
        })
        .thenApply(s -> {
            System.out.println("步骤2: 处理字符串: " + s);
            return s + " CompletableFuture";
        })
        .thenAccept(s -> {
            System.out.println("步骤3: 最终结果: " + s);
        })
        .join();
    }

    /**
     * CompletableFuture 组合
     */
    private static void testCompletableFutureCombine() throws Exception {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "结果A";
        });
        
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "结果B";
        });
        
        CompletableFuture<String> combined = future1.thenCombine(future2, (a, b) -> {
            return a + " + " + b;
        });
        
        System.out.println("组合结果: " + combined.get());
    }
}
