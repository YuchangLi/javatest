package com.java11.volatilee;

public class VolatileVisibilityTest {

    private static boolean flag = false;
    private static volatile boolean volatileFlag = false;

    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("=== 测试普通变量（无 volatile）的可见性问题 ===");
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 等待 flag 变为 true...");
            while (!flag) {
            }
            System.out.println(Thread.currentThread().getName() + " 检测到 flag 变为 true 了！");
        }, "普通变量线程");
        t1.start();

        Thread.sleep(1000);
        flag = true;
        System.out.println(Thread.currentThread().getName() + " 已将 flag 设置为 true");
        
        Thread.sleep(2000);
        System.out.println("如果普通变量线程没有输出，说明它没有看到 flag 的更新！");
        System.out.println();
        
        System.out.println("=== 测试 volatile 变量的可见性 ===");
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 等待 volatileFlag 变为 true...");
            while (!volatileFlag) {
            }
            System.out.println(Thread.currentThread().getName() + " 检测到 volatileFlag 变为 true 了！");
        }, "volatile变量线程");
        t2.start();

        Thread.sleep(1000);
        volatileFlag = true;
        System.out.println(Thread.currentThread().getName() + " 已将 volatileFlag 设置为 true");
        
        Thread.sleep(1000);
        System.out.println("volatile 变量线程应该能立即看到更新！");
    }
}
