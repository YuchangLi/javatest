package com.java11.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 基于 AQS 实现的简化版 CountDownLatch
 */
public class SimpleCountDownLatch {

    private final Sync sync;

    private static final class Sync extends AbstractQueuedSynchronizer {
        Sync(int count) {
            setState(count);
        }

        int getCount() {
            return getState();
        }

        @Override
        protected int tryAcquireShared(int acquires) {
            return getState() == 0 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int releases) {
            for (;;) {
                int c = getState();
                if (c == 0) return false;
                int next = c - 1;
                if (compareAndSetState(c, next)) {
                    return next == 0;
                }
            }
        }
    }

    public SimpleCountDownLatch(int count) {
        if (count < 0) throw new IllegalArgumentException("count < 0");
        this.sync = new Sync(count);
    }

    /**
     * 等待直到 count 为 0
     */
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    /**
     * 带超时的等待
     */
    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    /**
     * count 减 1
     */
    public void countDown() {
        sync.releaseShared(1);
    }

    /**
     * 获取当前 count
     */
    public long getCount() {
        return sync.getCount();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleCountDownLatch latch = new SimpleCountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName() + " 完成任务");
                    latch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Worker-" + i).start();
        }

        System.out.println("等待所有任务完成...");
        latch.await();
        System.out.println("所有任务完成，主线程继续执行");
    }
}
