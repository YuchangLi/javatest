package com.java11.aqs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConditionTest
 *
 * @param <T>
 */
public class SimpleBlockingQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    // 两个不同的条件队列：分别用于"队列不满"和"队列不空"
    private final Condition fullCon = lock.newCondition();
    private final Condition emptyCon = lock.newCondition();

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 向队列中添加元素，如果队列已满则等待。
     */
    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            // 队列满时，在 notFull 条件上等待
            while (queue.size() == capacity) {
                fullCon.await();
            }
            queue.offer(item);
            // 添加元素后，通知在 notEmpty 条件上等待的消费者线程
            emptyCon.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从队列中取出元素，如果队列为空则等待。
     */
    public T take() throws InterruptedException {
        lock.lock();
        try {
            // 队列空时，在 notEmpty 条件上等待
            while (queue.isEmpty()) {
                emptyCon.await();
            }
            T item = queue.poll();
            // 取出元素后，通知在 notFull 条件上等待的生产者线程
            fullCon.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(5);

        // 生产者线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    blockingQueue.put(i);
                    System.out.println("生产: " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        // 消费者线程
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    int item = blockingQueue.take();
                    System.out.println("消费: " + item);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();
    }
}