package com.java11.rxjava;

public class IntegerTest {
  
  public static void main(String[] args) {
    Integer a = 200;
    Integer b = 200;
    System.out.println(a == b);
    System.out.println(b);
    System.out.println(b.hashCode());
    change(a);
    System.out.println(a);
  }

  private static int change(int a) {
     return a = 2;
  }
}
