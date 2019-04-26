package com.java11.rxjava.gc;

public class FinalizeTest {
  public static FinalizeTest test;
  public void isAlive() {
      System.out.println("I am alive :)");
  }
  @Override
  protected void finalize() throws Throwable {
      super.finalize();
      System.out.println("finalize method executed!");
      test = this;
  }
  public static void main(String[] args)throws Exception {
      test = new FinalizeTest();
      test = null;
      System.gc();
      Thread.sleep(500);
      if (test != null) {
          test.isAlive();
      }else {
          System.out.println("no,I am dead :(");
      }
      // 下面代码与上面完全一致，但是此次自救失败
      test = null;
      System.gc();
      Thread.sleep(500);
      if (test != null) {
          test.isAlive();
      }else {
          System.out.println("no,I am dead :(");
      }
  }
                  }
