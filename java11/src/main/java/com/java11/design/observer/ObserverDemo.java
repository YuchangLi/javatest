package com.java11.design.observer;

public class ObserverDemo {

    interface Observer {
        void update(String event);
    }

    interface Subject {
        void addObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers();
    }

    private static class NewsPublisher implements Subject {
        private final java.util.List<Observer> observers = new java.util.ArrayList<>();
        private String latestNews;

        @Override
        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update(latestNews);
            }
        }

        public void publish(String news) {
            this.latestNews = news;
            System.out.println("\n[NewsPublisher] 发布新闻: " + news);
            notifyObservers();
        }
    }

    private static class NewsReader implements Observer {
        private final String name;

        NewsReader(String name) {
            this.name = name;
        }

        @Override
        public void update(String event) {
            System.out.println("  " + name + " 收到新闻: " + event);
        }
    }

    public static void main(String[] args) {
        NewsPublisher publisher = new NewsPublisher();

        Observer reader1 = new NewsReader("张三");
        Observer reader2 = new NewsReader("李四");
        Observer reader3 = new NewsReader("王五");

        System.out.println("===== 1. 注册观察者 =====");
        publisher.addObserver(reader1);
        publisher.addObserver(reader2);
        publisher.addObserver(reader3);

        publisher.publish("Java 11 正式发布！");

        System.out.println("\n===== 2. 移除观察者李四 =====");
        publisher.removeObserver(reader2);

        publisher.publish("观察者模式详解");

        System.out.println("\n===== 3. 使用 Java 自带 Observable（已废弃，仅作参考） =====");
        java.util.Observable legacyObservable = new java.util.Observable() {
            public void triggerChange(Object arg) {
                setChanged();
                notifyObservers(arg);
            }
        };
        legacyObservable.addObserver((o, arg) -> System.out.println("  Legacy Observer 收到: " + arg));
        ((java.util.Observable) legacyObservable).notifyObservers("不会触发，未调用 setChanged");
        legacyObservable.notifyObservers("也不会触发");
        try {
            legacyObservable.getClass().getDeclaredMethod("triggerChange", Object.class)
                    .invoke(legacyObservable, "通过反射触发 setChanged 后可以收到");
        } catch (Exception e) {
            System.out.println("  反射调用失败: " + e.getMessage());
        }

        System.out.println("\n===== 4. 使用 java.beans PropertyChangeListener =====");
        java.beans.PropertyChangeSupport pcs = new java.beans.PropertyChangeSupport(publisher);
        pcs.addPropertyChangeListener(evt ->
                System.out.println("  PropertyChangeListener 收到: " + evt.getPropertyName()
                        + " | 旧值: " + evt.getOldValue() + " | 新值: " + evt.getNewValue()));
        pcs.firePropertyChange("news", "观察者模式详解", "PropertyChangeListener 演示");
    }
}
