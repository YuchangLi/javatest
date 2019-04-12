package moduletest;

import java.util.Arrays;

import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;

public class FlowDemo {
  public static void main(String[] args) {
    // Create a publisher.

    SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

    // Create a subscriber and register it with the publisher.

    MySubscriber<String> subscriber = new MySubscriber<>();
    publisher.subscribe(subscriber);

    // Publish several data items and then close the publisher.

    System.out.println("Publishing data items...");
    String[] items =
        {"1", "2", "3", "4", "5"};
    Arrays.asList(items).stream().forEach(i -> {
      try {
        publisher.submit(i);
        Thread.sleep(1000);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    publisher.close();

    try {
      synchronized ("A") {
        "A".wait();
      }
    } catch (InterruptedException ie) {
    }
    System.out.println("完成");
  }
}


class MySubscriber<T> implements Subscriber<T> {
  private Subscription subscription;

  @Override
  public void onSubscribe(Subscription subscription) {
    System.out.println("onSubscribe success");
    this.subscription = subscription;
    subscription.request(1);
  }

  @Override
  public void onNext(T item) {
    System.out.println("onNext Received: " + item);
    subscription.request(1);
  }

  @Override
  public void onError(Throwable t) {
    System.out.println("onError" + t.getMessage());
    t.printStackTrace();
    synchronized ("A") {
      "A".notifyAll();
    }
  }

  @Override
  public void onComplete() {
    System.out.println("onComplete");
    synchronized ("A") {
      "A".notifyAll();
    }
  }
}
