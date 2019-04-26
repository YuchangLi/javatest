package com.java11.rxjava.design.single;

public class SingleStaticTest {
  private SingleStaticTest() {System.out.println("in SingleStaticTest");}
  
  static class SingleStaticTestHandler {
    public final static SingleStaticTest single = new SingleStaticTest();
  }
 
  public static void main(String[] args) {
    SingleVolatileTest single = SingleVolatileTest.getSingle();
    System.out.println("single = "+single);
    SingleVolatileTest single2 = SingleVolatileTest.getSingle();
    System.out.println("single2 = "+single2);
  }
}
