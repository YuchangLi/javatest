package com.java11.rxjava.atomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AtomicLongTest {
  
  static AtomicLong l = new AtomicLong(1);
  
  static LongAdder l2 = new LongAdder();
  
  public static void main(String[] args) {
    System.out.println(l.addAndGet(1));
    System.out.println("l2 = " + l2.longValue());
    l2.increment();
    System.out.println("l2 = " + l2.longValue());
  }
}
