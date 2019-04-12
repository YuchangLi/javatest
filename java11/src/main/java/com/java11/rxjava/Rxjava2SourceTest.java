package com.java11.rxjava;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class Rxjava2SourceTest {
  public static void main(String[] args) {
    flowable();
//    single();
  }

  // 0..N flows, supporting Reactive-Streams and backpressure
  private static void flowable() {
    Flowable.just("Hello world").subscribe(System.out::println);
    
    Flowable
      .range(1, 5)
      .subscribe(System.out::println);
  }

  private static void single() {
    Single.just(1).subscribe(new SingleObserver<Integer>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" onSubscribe at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
      }

      @Override
      public void onSuccess(@NonNull Integer integer) {
        System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" onSuccess at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("single : onSuccess : "+integer );
      }

      @Override
      public void onError(@NonNull Throwable e) {
        System.out.println("single : onError : "+e.getMessage()+"\n");
      }
  });
  }
  
}
