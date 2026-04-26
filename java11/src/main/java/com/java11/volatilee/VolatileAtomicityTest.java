package com.java11.volatilee;

public class VolatileAtomicityTest {

    private static volatile int count = 0;

    public static void increment() {
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("=== 测试 volatile 不保证原子性 ===");
        System.out.println("启动 10 个线程，每个线程对 count 递增 10000 次");
        System.out.println("预期结果: 100000");
        System.out.println();

        Thread[] threads = new Thread[10];
        
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increment();
                }
            }, "Thread-" + i);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("实际结果: " + count);
        System.out.println();
        System.out.println("说明: 实际结果通常小于 100000，说明 volatile 只保证可见性，不保证原子性！");
        System.out.println("count++ 操作包含三个步骤：读取、增加、写入，这三个步骤不是原子的");
    }
}
