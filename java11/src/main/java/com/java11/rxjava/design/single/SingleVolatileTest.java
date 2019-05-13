package com.java11.rxjava.design.single;

public class SingleVolatileTest {
  
  private SingleVolatileTest() {System.out.println("in SingleVolatileTest");}
  
  private static volatile SingleVolatileTest single;
  
  public static SingleVolatileTest getSingle() {
    if(single == null) {
      synchronized(SingleVolatileTest.class) {
        if(single == null ) {
          single = new SingleVolatileTest();
        }
      }
    }
    return single;
  }
  
  public static void main(String[] args) {
    SingleStaticTest single = SingleStaticTest.SingleStaticTestHandler.single;
    System.out.println("single = "+single);
    SingleStaticTest single2 = SingleStaticTest.SingleStaticTestHandler.single;
    System.out.println("single2 = "+single2);
  }
}
