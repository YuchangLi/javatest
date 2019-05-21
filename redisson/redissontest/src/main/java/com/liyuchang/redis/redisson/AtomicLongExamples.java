package com.liyuchang.redis.redisson;


import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;

public class AtomicLongExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RAtomicLong atomicLong = redisson.getAtomicLong("myLong");
        System.out.println(atomicLong.get());
        atomicLong.getAndDecrement();
        System.out.println(atomicLong.get());
        atomicLong.getAndIncrement();
        System.out.println(atomicLong.get());

        atomicLong.addAndGet(10L);
        atomicLong.compareAndSet(29, 412);

        atomicLong.decrementAndGet();
        atomicLong.incrementAndGet();

        atomicLong.getAndAdd(302);
        atomicLong.getAndDecrement();
        atomicLong.getAndIncrement();

        redisson.shutdown();
    }
}