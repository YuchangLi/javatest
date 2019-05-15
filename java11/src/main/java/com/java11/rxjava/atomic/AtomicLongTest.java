package com.java11.rxjava.atomic;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AtomicLongTest {
  
  static AtomicLong l = new AtomicLong(1);
  
  static LongAdder l2 = new LongAdder();
  
  public static void main(String[] args) {
//    System.out.println(l.addAndGet(1));
//    System.out.println("l2 = " + l2.longValue());
//    l2.increment();
//    System.out.println("l2 = " + l2.longValue());
    
    int[] as = {1, 2, 3, 4};
    
    int count = 0;
    
    for(int i=0; i<as.length; i++) {
      int one = as[i];
      int[] as2 = remove(as, i);
      for(int j=0; j< as2.length; j++) {
        int two = as2[j];
        int[] as3 = remove(as2, j);
        for(int k=0; k<as3.length; k++) {
          int three = as3[k];
          System.out.println(++count+": "+ one+""+two+""+three);
        }
      }
    }
  }

  /**
   * 返回
   * @param i
   * @return int
   */
  private static int add(int i) {
    if(i==1) return i;
    return i+add(i - 1);
  }

  private static int[] remove(int[] as, int index) {
    int[] as2 = new int[as.length-1];
    for(int i=0; i<index; i++) {
      as2[i] = as[i];
    }
    for(int j=index+1; j<as.length; j++) {
      as2[j-1] = as[j];
    }
    return as2;
  }
}
