package com.java11.rxjava.gc;

public class PrinrtGCTest {
  public Object instance = null;
  private static int _1MB = 1024 * 1024;
  private byte[] bigSize = new byte[2 * _1MB];

  public static void testGC() {
    PrinrtGCTest test1 = new PrinrtGCTest();
    PrinrtGCTest test2 = new PrinrtGCTest();
    test1.instance = test2;
    test2.instance = test1;
    test1 = null;
    test2 = null;
    // 强制JVM进行垃圾回收
    System.gc();
  }

  public static void main(String[] args) {
//    testGC();
//    System.out.println("123");
    int i = 1;
    int a = ++i;
    System.out.println(a);
  }
}
