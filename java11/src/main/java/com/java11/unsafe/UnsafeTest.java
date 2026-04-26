package com.java11.unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class UnsafeTest {

    private static sun.misc.Unsafe unsafe;

    static {
        try {
            Field theUnsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafe = (sun.misc.Unsafe) theUnsafeField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class TestObject {
        private int intValue;
        private long longValue;
        private String stringValue;
    }

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {
        testDirectMemory();
        testObjectFieldOperations();
        testCASOperation();
        testParkUnpark();
    }

    private static void testDirectMemory() {
        System.out.println("=== 直接操作内存 ===");
        long address = unsafe.allocateMemory(8);
        try {
            unsafe.putLong(address, 123456789L);
            long value = unsafe.getLong(address);
            System.out.println("写入内存的值: " + value);
            System.out.println("从内存读取的值: " + unsafe.getLong(address));
        } finally {
            unsafe.freeMemory(address);
        }
    }

    private static void testObjectFieldOperations() throws NoSuchFieldException {
        System.out.println("\n=== 对象属性操作 ===");
        TestObject obj = new TestObject();

        long intValueOffset = unsafe.objectFieldOffset(TestObject.class.getDeclaredField("intValue"));
        long longValueOffset = unsafe.objectFieldOffset(TestObject.class.getDeclaredField("longValue"));
        long stringValueOffset = unsafe.objectFieldOffset(TestObject.class.getDeclaredField("stringValue"));

        unsafe.putInt(obj, intValueOffset, 100);
        unsafe.putLong(obj, longValueOffset, 999L);
        unsafe.putObject(obj, stringValueOffset, "Hello Unsafe");

        System.out.println("intValue: " + unsafe.getInt(obj, intValueOffset));
        System.out.println("longValue: " + unsafe.getLong(obj, longValueOffset));
        System.out.println("stringValue: " + unsafe.getObject(obj, stringValueOffset));
    }

    private static void testCASOperation() throws NoSuchFieldException {
        System.out.println("\n=== CAS操作 ===");
        TestObject obj = new TestObject();
        long intValueOffset = unsafe.objectFieldOffset(TestObject.class.getDeclaredField("intValue"));

        unsafe.putInt(obj, intValueOffset, 0);

        boolean success1 = unsafe.compareAndSwapInt(obj, intValueOffset, 0, 1);
        System.out.println("CAS 0→1 成功: " + success1 + ", 结果: " + unsafe.getInt(obj, intValueOffset));

        boolean success2 = unsafe.compareAndSwapInt(obj, intValueOffset, 0, 2);
        System.out.println("CAS 0→2 成功: " + success2 + ", 结果: " + unsafe.getInt(obj, intValueOffset));
    }

    private static void testParkUnpark() throws InterruptedException {
        System.out.println("\n=== 线程 park/unpark 操作 ===");
        Thread mainThread = Thread.currentThread();

        Thread unparkThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("子线程调用 unpark 唤醒主线程");
                unsafe.unpark(mainThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        unparkThread.start();
        System.out.println("主线程调用 park 阻塞");
        unsafe.park(false, 0L);
        System.out.println("主线程被唤醒，继续执行");
    }
}
