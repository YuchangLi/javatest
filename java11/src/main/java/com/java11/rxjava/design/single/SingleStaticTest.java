package com.java11.rxjava.design.single;

public class SingleStaticTest {
  private SingleStaticTest() {System.out.println("in SingleStaticTest");}
  volatile static String[] array = new String[] {"1,2,3"};
  static class SingleStaticTestHandler {
    public final static SingleStaticTest single = new SingleStaticTest();
  }
 
  public static void main(String[] args) {
//    volatile String[] array2 = new String[] {"1,2,3"};
    SingleVolatileTest single = SingleVolatileTest.getSingle();
    System.out.println("single = "+single);
    SingleVolatileTest single2 = SingleVolatileTest.getSingle();
    System.out.println("single2 = "+single2);
    System.out.println(array);
  }
}
