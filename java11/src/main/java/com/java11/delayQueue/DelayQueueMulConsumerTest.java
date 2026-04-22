package com.java11.delayQueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;

public class DelayQueueMulConsumerTest {
    public static void main(String[] args) {
        DelayQueue<DelayQueueTest.DelayTask> queue = new DelayQueue<>();
        queue.put(new DelayQueueTest.DelayTask("任务1", 3000));
        // queue.put(new DelayQueueTest.DelayTask("任务2", 2000));
        // queue.put(new DelayQueueTest.DelayTask("任务3", 2000));
        // 创建3个消费者
        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                try {
                    String s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                    System.out.println(s1 + " " + Thread.currentThread().getName() + " 开始等待...");
                    DelayQueueTest.DelayTask task = queue.take(); // 只有1个线程能拿到
                    String s12 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                    System.out.println(s12 + " " + Thread.currentThread().getName() + " 拿到: " + task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Consumer-" + i).start();
        }
    }
}
