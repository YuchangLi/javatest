package com.java11.rxjava;


import java.util.List;
import java.util.stream.Stream;

import io.reactivex.Observable;

public class Rxjava2Java11Test {
  public static void main(String[] args) {
    // Flowable.just("Hello world").subscribe(System.out::println);

    // Flowable.range(1, 10)
    // .flatMap(v ->
    // Flowable.just(v)
    // .subscribeOn(Schedulers.computation())
    // .map(w -> w * w)
    // )
    // .blockingSubscribe(System.out::println);

    // Flowable.range(1, 10)
    // .parallel()
    // .runOn(Schedulers.computation())
    // .map(v -> v * v)
    // .sequential()
    // .blockingSubscribe(System.out::println);


    Observable.just("Hello, world!", "nihao").subscribe(System.out::println); // 发送"Hello, // world!"

    // 使用defer( )，有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable
    Observable.defer(() -> Observable.just("deferObservable")).subscribe(System.out::println);

    // 使用interval( ),创建一个按固定时间间隔发射整数序列的Observable，可用作定时器
//    Observable.interval(3, TimeUnit.SECONDS).just("interval").subscribe(System.out::println);
    
    Observable.range(10, 5).subscribe(System.out::println);
    
    Observable.just("1",  "2",  "3")
      .map(i->Integer.valueOf(i)+10)
      .subscribe(System.out::println); // 发送"Hello, // world!"
    
    Stream.of(List.of("1","2"), List.of("3","4")).flatMap(str->str.stream()).forEach(System.out::println);

  }
}
