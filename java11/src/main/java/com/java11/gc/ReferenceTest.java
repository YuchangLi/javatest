package com.java11.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceTest {

  private static final int _1MB = 1024 * 1024;

  public static void strongReference() {
    Object obj = new Object();
    System.gc();
    System.out.println("Strong reference: " + obj);
  }

  public static void softReference() {
    SoftReference<byte[]> ref = new SoftReference<>(new byte[10 * _1MB]);
    byte[] data = ref.get();
    System.out.println("Soft reference (before GC): " + (data != null));
    System.gc();
    data = ref.get();
    System.out.println("Soft reference (after GC): " + (data != null));
  }

  public static void weakReference() {
    WeakReference<Object> ref = new WeakReference<>(new Object());
    System.out.println("Weak reference (before GC): " + (ref.get() != null));
    System.gc();
    System.out.println("Weak reference (after GC): " + (ref.get() != null));
  }

  public static void phantomReference() throws InterruptedException {
    ReferenceQueue<Object> queue = new ReferenceQueue<>();
    PhantomReference<Object> ref = new PhantomReference<>(new Object(), queue);
    System.out.println("Phantom reference (get): " + (ref.get() != null));
    System.gc();
    Thread.sleep(200);
    System.out.println("Phantom reference (enqueued): " + (queue.poll() != null));
  }

  public static void main(String[] args) throws Exception {
    strongReference();
    System.out.println("===========================");
    softReference();
    System.out.println("===========================");
    weakReference();
    System.out.println("===========================");
    phantomReference();
  }
}
