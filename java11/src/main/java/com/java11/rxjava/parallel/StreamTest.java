package com.java11.rxjava.parallel;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamTest {

  public static void main(String[] args) {
      System.out.println("sum = " + parallelRangedSum(1_000_000));
  }

  public static long parallelRangedSum(long n) {
      return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
  }
}