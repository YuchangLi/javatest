package com.java11.rxjava.thread;

public class PendingInterrupt extends Object {  
  public static void main(String[] args){  
      //如果输入了参数，则在mian线程中中断当前线程（亦即main线程）  
    System.out.println("Point X: Thread.interrupted()=" + Thread.currentThread().isInterrupted());  
    if( true ){
          Thread.currentThread().interrupt();
          // Thread.interrupted(), 若已interrupted，则返回true且标志为false
          System.out.println(Thread.interrupted());
      }   
      System.out.println("Point X: Thread.interrupted()=" + Thread.currentThread().isInterrupted());  
      //获取当前时间  
      long startTime = System.currentTimeMillis();  
      try{  
          Thread.sleep(2000);  
          System.out.println("was NOT interrupted");  
      }catch(InterruptedException x){  
          System.out.println("was interrupted");  
      }  
      //计算中间代码执行的时间  
      System.out.println("elapsedTime=" + ( System.currentTimeMillis() - startTime));  
  }  
}