package com.java11.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class SimpleSemaphore {

    private final Sync sync;

    private static final class Sync extends AbstractQueuedSynchronizer {
        Sync(int permits) {
            setState(permits);
        }

        int getPermits() {
            return getState();
        }

        @Override
        protected int tryAcquireShared(int acquires) {
            for (;;) {
                int available = getState();
                int remaining = available - acquires;
                if (remaining < 0) return -1;
                if (compareAndSetState(available, remaining)) {
                    return remaining;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int releases) {
            for (;;) {
                int available = getState();
                int next = available + releases;
                if (compareAndSetState(available, next)) {
                    return true;
                }
            }
        }
    }

    public SimpleSemaphore(int permits) {
        if (permits < 0) throw new IllegalArgumentException("permits < 0");
        this.sync = new Sync(permits);
    }

    public void acquire() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public boolean tryAcquire() {
        return sync.tryAcquireShared(1) >= 0;
    }

    public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    public void release() {
        sync.releaseShared(1);
    }

    public int availablePermits() {
        return sync.getPermits();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleSemaphore semaphore = new SimpleSemaphore(2);
        System.out.println("初始许可数: " + semaphore.availablePermits());

        for (int i = 0; i < 5; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    System.out.println("线程 " + id + " 尝试获取许可...");
                    semaphore.acquire();
                    System.out.println("线程 " + id + " 获取到许可，当前可用: " + semaphore.availablePermits());
                    Thread.sleep(1000);
                    System.out.println("线程 " + id + " 释放许可");
                    semaphore.release();
                    System.out.println("线程 " + id + " 释放完毕，当前可用: " + semaphore.availablePermits());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Worker-" + i).start();
        }

        Thread.sleep(6000);
        System.out.println("最终可用许可: " + semaphore.availablePermits());
    }
}