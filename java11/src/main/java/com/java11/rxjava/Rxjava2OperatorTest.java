package com.java11.rxjava;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Rxjava2OperatorTest {
  public static void main(String[] args) {
//    create();
//    map();
//    zip();
//    concat();
//    flatMap();
//    distinct();
//    filter();
//    buffer();
//    timer();
//    doOnNext();
//    skipAndTake();
//    debounce();
//    defer();
//    merge();
//    reduce();
    window();
  }
  static void create() {
    System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" start : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
        System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" subscribe : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
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
    })
    .subscribeOn(Schedulers.newThread())
    .observeOn(Schedulers.newThread())
    .subscribe(new Observer<Integer>() {
        private int i;
        private Disposable mDisposable;
  
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            System.out.print("onSubscribe, isDisposed : " + d.isDisposed() + "\n" );
            mDisposable = d;
        }
  
        @Override
        public void onNext(@NonNull Integer integer) {
            System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" onNext : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
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
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
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
    System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" timer start : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
    Observable.just(1, 2, 3, 4, 5)
    .buffer(3, 2)
    .subscribe(new Consumer<List<Integer>>() {
        @Override
        public void accept(@NonNull List<Integer> integers) throws Exception {
          System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" timer accept : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
            System.out.println("buffer List.size : " + integers.size() + ", List.value : "+integers);
//            for (Integer i : integers) {
//              System.out.print( i + ", ");
//            }
        }
    });
  }
  
  static void timer() {
    System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" timer start : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
    Disposable disposable = Observable
//      .timer(2, TimeUnit.SECONDS)
      .interval(0, 1, TimeUnit.SECONDS)
      .subscribeOn(Schedulers.io())
//      .observeOn(Schedulers.newThread()) // timer 默认在新线程，所以需要切换回主线程
      .subscribe(new Consumer<Long>() {
          @Override
          public void accept(@NonNull Long aLong) throws Exception {
            System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" timer :" + aLong + " at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
          }
      });
    try {
      Thread.sleep(10000);
      disposable.dispose();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  static void doOnNext() {
    Observable.just(1, 2, 3, 4)
    .doOnNext(new Consumer<Integer>() {
        @Override
        public void accept(@NonNull Integer integer) throws Exception {
          System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" doOnNext at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
          System.out.println("doOnNext 保存 " + integer + "成功" + "\n");
        }
      }).subscribe(new Consumer<Integer>() {
        @Override
        public void accept(@NonNull Integer integer) throws Exception {
          System.out.println(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" subscribe at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
          System.out.println("doOnNext :" + integer + "\n");
        }
      });
  }
   
  static void skipAndTake() {
    Observable.just(1, 2, 3, 4, 5, 6)
    .skip(2)
    .take(2)
    .subscribe(System.out::println);
  }
  
  static void debounce() {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
          // send events with simulated time wait
          emitter.onNext(1); // skip
          Thread.sleep(400);
          emitter.onNext(2); // deliver
          Thread.sleep(505);
          emitter.onNext(3); // skip
          Thread.sleep(100);
          emitter.onNext(4); // deliver
          Thread.sleep(605);
          emitter.onNext(5); // deliver
          Thread.sleep(510);
          emitter.onComplete();
      }
    }).debounce(500, TimeUnit.MILLISECONDS)
//      .subscribeOn(Schedulers.io())
//      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(integer->System.out.println("debounce :" + integer + "\n"));
  }
  
  static void defer() {
    Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
      @Override
      public ObservableSource<Integer> call() throws Exception {
        System.out.print(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" ObservableSource at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
          return Observable.just(1);
      }
    });
//    Observable<Integer> observable = Observable.create(e->{
//      System.out.print(Thread.currentThread().getId()+",name "+Thread.currentThread().getName()+" ObservableSource at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
//      e.onNext(1);
//    });
    observable
     .subscribe(new Observer<Integer>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
          System.out.println("onSubscribe");
        }
  
        @Override
        public void onNext(@NonNull Integer integer) {
          System.out.print("defer : " + integer + "\n");
        }
  
        @Override
        public void onError(@NonNull Throwable e) {
          System.out.print("defer : onError : " + e.getMessage() + "\n");
        }
  
        @Override
        public void onComplete() {
          System.out.print("defer : onComplete\n");
        }
     });
    observable
    .subscribe(new Observer<Integer>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe");
      }
      
      @Override
      public void onNext(@NonNull Integer integer) {
        System.out.print("defer : " + integer + "\n");
      }
      
      @Override
      public void onError(@NonNull Throwable e) {
        System.out.print("defer : onError : " + e.getMessage() + "\n");
      }
      
      @Override
      public void onComplete() {
        System.out.print("defer : onComplete\n");
      }
    });
  }

  // 注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送
  static void merge() {
    Observable.concat(Observable.just(1, 2, 5), Observable.just(3, 4)).subscribe(System.out::println);
    Observable.merge(Observable.just(1, 2, 5), Observable.just(3, 4)).subscribe(System.out::println);
  }
  
  static void reduce() {
    Observable.just(1, 2, 3, 4)
//      .reduce(new BiFunction<Integer, Integer, Integer>() {
      .scan(new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
            return integer + integer2;
        }
    }).subscribe(System.out::println);
  }
  
  static void window() {
    Observable.interval(1, TimeUnit.SECONDS) // 间隔一秒发一次
    .take(6) // 最多接收15个
    .window(2, TimeUnit.SECONDS)
    .subscribeOn(Schedulers.io())
//    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Consumer<Observable<Long>>() {
        @Override
        public void accept(@NonNull Observable<Long> longObservable) throws Exception {
          System.out.print("Sub Divide begin...\n");
            longObservable.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@NonNull Long aLong) throws Exception {
                          System.out.print("Next:" + aLong + "\n");
                        }
                    });
        }
    });
    try {
      Thread.sleep(15000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

