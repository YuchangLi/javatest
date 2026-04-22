package com.java11.delayQueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue测试示例
 * DelayQueue是一个无界阻塞队列，只有在延迟期满时才能从中提取元素
 */
public class DelayQueueTest {

    /**
     * 延迟任务类，实现Delayed接口
     */
    static class DelayTask implements Delayed {
        private final String taskName;
        private final long delayTime; // 延迟时间（毫秒）
        private final long expireTime; // 到期时间

        public DelayTask(String taskName, long delayTime) {
            this.taskName = taskName;
            this.delayTime = delayTime;
            this.expireTime = System.currentTimeMillis() + delayTime;
        }

        /**
         * 获取剩余延迟时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            long remaining = expireTime - System.currentTimeMillis();
            System.out.println("remaining: "+remaining);
            return unit.convert(remaining, TimeUnit.MILLISECONDS);
        }

        /**
         * 比较两个延迟任务的到期时间
         */
        @Override
        public int compareTo(Delayed other) {
            if (other instanceof DelayTask) {
                DelayTask otherTask = (DelayTask) other;
                return Long.compare(this.expireTime, otherTask.expireTime);
            }
            return Long.compare(this.expireTime, ((DelayTask) other).expireTime);
        }

        public String getTaskName() {
            return taskName;
        }

        public long getDelayTime() {
            return delayTime;
        }

        @Override
        public String toString() {
            return "DelayTask{name='" + taskName + "', delay=" + delayTime + "ms}";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建DelayQueue
        DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

        System.out.println("=== DelayQueue 测试开始 ===\n");

        // 添加多个不同延迟时间的任务
        delayQueue.put(new DelayTask("任务1", 3000)); // 3秒后执行
        delayQueue.put(new DelayTask("任务2", 1000)); // 1秒后执行
        delayQueue.put(new DelayTask("任务3", 5000)); // 5秒后执行
        delayQueue.put(new DelayTask("任务4", 2000)); // 2秒后执行

        System.out.println("已添加4个任务到DelayQueue\n");
        System.out.println("队列大小: " + delayQueue.size());
        System.out.println("队首元素（不删除）: " + delayQueue.peek());
        String times = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(times);

        // 启动消费者线程
        Thread consumerThread = new Thread(() -> {
            try {
                while (!delayQueue.isEmpty()) {
                    // take()方法会阻塞直到有元素到期
                    DelayTask task = delayQueue.take();
                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    System.out.println(time+"：[" + Thread.currentThread().getName() + "] 执行: " + task
                            + " | 实际等待时间: " + task.getDelayTime() + "ms");
                }
                System.out.println("\n所有任务执行完毕！");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("消费者线程被中断");
            }
        }, "Consumer-Thread");

        consumerThread.start();

        // 主线程等待消费者线程完成
        consumerThread.join();

        System.out.println("\n=== DelayQueue 测试结束 ===");
        
        // 演示poll方法（非阻塞）
        System.out.println("\n=== 演示poll方法 ===");
        DelayQueue<DelayTask> queue2 = new DelayQueue<>();
        queue2.put(new DelayTask("快速任务", 500));
        
        System.out.println("立即poll（可能为null）: " + queue2.poll());
        Thread.sleep(600);
        System.out.println("等待600ms后poll: " + queue2.poll());
    }
}
