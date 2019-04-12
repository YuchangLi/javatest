package com.java11.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class Rxjava2OperatorTest {
  public static void main(String[] args) {
//    create();
//    map();
//    zip();
//    concat();
//    flatMap();
//    distinct();
//    filter();
    buffer();
  }
  static void create() {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
          System.out.print("Observable emit 1" + "\n");
          e.onNext(1);
          System.out.print("Observable emit 2" + "\n");
          e.onNext(2);
          System.out.print("Observable emit 3" + "\n");
          e.onNext(3);
          e.onComplete();
          System.out.print("Observable emit 4" + "\n" );
          e.onNext(4);
      }
    }).subscribe(new Observer<Integer>() {
        private int i;
        private Disposable mDisposable;
  
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            System.out.print("onSubscribe, isDisposed : " + d.isDisposed() + "\n" );
            mDisposable = d;
        }
  
        @Override
        public void onNext(@NonNull Integer integer) {
            System.out.print("onNext : value : " + integer + "\n" );
            i++;
            if (i == 2) {
                // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                mDisposable.dispose();
                System.out.print("onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
            }
        }
  
        @Override
        public void onError(@NonNull Throwable e) {
            System.out.print("onError : value : " + e.getMessage() + "\n" );
        }
  
        @Override
        public void onComplete() {
            System.out.print("onComplete" + "\n" );
        }
    });
  }

  static void map() {
    Observable
      .create(e->{
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
      })
      .map(integer->"This is result " + integer)
      .subscribe(System.out::println);
  }
  
  static void zip() {
    Observable.zip(
        // String source
        Observable.create(e->{
          if (!e.isDisposed()) {
            e.onNext("A");
            System.out.print("String emit : A \n");
            e.onNext("B");
            System.out.print("String emit : B \n");
            e.onNext("C");
            System.out.print("String emit : C \n");
          }
        }),
        // Integer source
        Observable.create(e->{
          if (!e.isDisposed()) {
            e.onNext(1);
            System.out.print("int emit : 1 \n");
            e.onNext(2);
            System.out.print("int emit : 2 \n");
            e.onNext(3);
            System.out.print("int emit : 3 \n");
            e.onNext(4);
            System.out.print("int emit : 4 \n");
          }
        }), 
       // zipper
      (String s, Integer integer)->s + integer
    ).subscribe(s->System.out.print("zip : accept : " + s + "\n"));
  }
  
  static void concat() {
    Observable.concat(Observable.just(1,2,3), Observable.just("a", "b", "c")).subscribe(System.out::println);
  }

  static void flatMap() {
    Observable.just(1,2,3)
    // faltMap无序 ，concatMap有序
    .flatMap
//    .concatMap 
    (new Function<Integer, ObservableSource<String>>() {
      @Override
      public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
          List<String> list = new ArrayList<>();
          for (int i = 0; i < 3; i++) {
              list.add("I am value " + integer);
          }
          int delayTime = (int) (1 + Math.random() * 10);
          return Observable.fromIterable(list)
              .delay(delayTime, TimeUnit.MILLISECONDS)
              ;
      }
  })
//    .subscribeOn(Schedulers.newThread())
//    .observeOn(Schedulers.newThread())
    .subscribe(s->System.out.println("flatMap : accept : " + s));
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  static void distinct() {
    Observable.just(1,2,3,4,4,4,5).distinct().subscribe(System.out::println);
  }
  
  static void filter() {
    Observable.just(1,2,3,4,4,4,5).filter(i->i>3)
    .subscribe(System.out::println);
  }

  static void buffer() {
    Observable.just(1, 2, 3, 4, 5)
    .buffer(3, 2)
    .subscribe(new Consumer<List<Integer>>() {
        @Override
        public void accept(@NonNull List<Integer> integers) throws Exception {
            System.out.println("buffer List.size : " + integers.size() + ", List.value : "+integers);
//            for (Integer i : integers) {
//              System.out.print( i + ", ");
//            }
        }
    });
  }
  
  
}

