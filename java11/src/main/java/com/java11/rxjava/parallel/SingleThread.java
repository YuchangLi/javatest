package com.java11.rxjava.parallel;

import java.util.stream.LongStream;

public class SingleThread {

  public static long[] numbers;

  // 12427419
  public static void main(String[] args) {
      numbers = LongStream.rangeClosed(1, 10_000_000).toArray();
      long t1 = System.currentTimeMillis();
      long t2 = System.nanoTime();
      long sum = 0;
      for (int i = 0; i < numbers.length; i++) {
          sum += numbers[i];
      }
      System.out.println("sum  = " + sum+" time = "+(System.currentTimeMillis() - t1)+", nanotime = "+(System.nanoTime()-t2));
  }

}
