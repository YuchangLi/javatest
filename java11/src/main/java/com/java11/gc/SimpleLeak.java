package com.java11.gc;

import java.util.ArrayList;
import java.util.List;

public class SimpleLeak {

    // 静态集合，生命周期与应用程序一样长
    public static List<byte[]> staticList = new ArrayList<>();

    public void leakMethod() {
        // 每次调用都向静态集合中添加一个 1MB 的字节数组
        staticList.add(new byte[1024 * 1024]); // 1MB
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleLeak leak = new SimpleLeak();
        System.out.println("Starting leak simulation...");

        // 循环添加对象，模拟内存泄漏过程
        for (int i = 0; i < 200; i++) {
            leak.leakMethod();
            System.out.println("Added " + (i + 1) + " MB to the list.");
            Thread.sleep(200); // 稍微延时，方便观察
        }

        System.out.println("Leak simulation finished. Keeping process alive for Heap Dump.");
        // 保持进程存活，以便我们有时间生成 Heap Dump
        Thread.sleep(Long.MAX_VALUE);
    }
}