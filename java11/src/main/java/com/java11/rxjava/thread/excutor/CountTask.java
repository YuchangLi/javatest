package com.java11.rxjava.thread.excutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer>{
  private static final long serialVersionUID = 115709533454238975L;

  private static final int THREAD_HOLD = 2;

  private int start;
  private int end;

  public CountTask(int start,int end){
      this.start = start;
      this.end = end;
  }

  @Override
  protected Integer compute() {
      int sum = 0;
      //如果任务足够小就计算
      boolean canCompute = (end - start) <= THREAD_HOLD;
      if(canCompute){
        if(start == 1) {
          try {
            Thread.sleep(3000);
//            int i = 1/0;
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
          for(int i=start;i<=end;i++){
              sum += i;
          }
      }else{
          int middle = (start + end) / 2;
          CountTask left = new CountTask(start,middle);
          CountTask right = new CountTask(middle+1,end);
          //执行子任务
          left.fork();
          right.fork();
          //获取子任务结果
          int lResult = left.join();
          System.out.println("lResult = "+lResult);
          int rResult = right.join();
          System.out.println("rResult = "+rResult);
          sum = lResult + rResult;
      }
      return sum;
  }

  public static void main(String[] args){
      ForkJoinPool pool = new ForkJoinPool();
      CountTask task = new CountTask(1,4);
      System.out.println(task.isCompletedNormally());
      Future<Integer> result = pool.submit(task);
      System.out.println("printStackTrace start");
      try {
          System.out.println(result.get());
          if(task.isCompletedAbnormally())
          {
            System.out.println(task.getException());
          }
          System.out.println("printStackTrace end");
          System.out.println(task.isCompletedAbnormally());
      } catch (InterruptedException e) {
          e.printStackTrace();
      } catch (ExecutionException e) {
          e.printStackTrace();
      }
  }
}
