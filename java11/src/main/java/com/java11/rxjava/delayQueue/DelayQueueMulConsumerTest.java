package com.java11.rxjava.delayQueue;

import java.util.concurrent.DelayQueue;

public class DelayQueueMulConsumerTest {
    public static void main(String[] args) {
        DelayQueue<DelayQueueTest.DelayTask> queue = new DelayQueue<>();
        queue.put(new DelayQueueTest.DelayTask("任务1", 2000));
        queue.put(new DelayQueueTest.DelayTask("任务2", 2000));
        queue.put(new DelayQueueTest.DelayTask("任务3", 2000));
        // 创建3个消费者
        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 开始等待...");
                    DelayQueueTest.DelayTask task = queue.take(); // 只有1个线程能拿到
                    System.out.println(Thread.currentThread().getName() + " 拿到: " + task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Consumer-" + i).start();
        }
    }
}
