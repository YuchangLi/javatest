package com.java11.thread;

public class StaticSingleton {
  private StaticSingleton(){}
  
  private static class StaticSingletonHand {
    private static StaticSingleton staticSingleton = new StaticSingleton();
  }
  
  public static StaticSingleton getStaticSingleton() {
    return StaticSingletonHand.staticSingleton;
  }
}
