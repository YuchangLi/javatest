package com.java11.design.single;

public class SingletonDemo {

    private SingletonDemo() {}

    private static class EagerSingleton {
        private static final EagerSingleton INSTANCE = new EagerSingleton();

        private EagerSingleton() {
            System.out.println("EagerSingleton 实例化");
        }

        public static EagerSingleton getInstance() {
            return INSTANCE;
        }
    }

    private static class LazySingletonUnsafe {
        private static LazySingletonUnsafe instance;

        private LazySingletonUnsafe() {
            System.out.println("LazySingletonUnsafe 实例化");
        }

        public static LazySingletonUnsafe getInstance() {
            if (instance == null) {
                instance = new LazySingletonUnsafe();
            }
            return instance;
        }
    }

    private static class LazySingletonSync {
        private static LazySingletonSync instance;

        private LazySingletonSync() {
            System.out.println("LazySingletonSync 实例化");
        }

        public static synchronized LazySingletonSync getInstance() {
            if (instance == null) {
                instance = new LazySingletonSync();
            }
            return instance;
        }
    }

    private static class DCLSingleton {
        private static volatile DCLSingleton instance;

        private DCLSingleton() {
            System.out.println("DCLSingleton 实例化");
        }

        public static DCLSingleton getInstance() {
            if (instance == null) {
                synchronized (DCLSingleton.class) {
                    if (instance == null) {
                        instance = new DCLSingleton();
                    }
                }
            }
            return instance;
        }
    }

    private static class HolderSingleton {
        private HolderSingleton() {
            System.out.println("HolderSingleton 实例化");
        }

        private static class Holder {
            private static final HolderSingleton INSTANCE = new HolderSingleton();
        }

        public static HolderSingleton getInstance() {
            return Holder.INSTANCE;
        }
    }

    private enum EnumSingleton {
        INSTANCE;

        EnumSingleton() {
            System.out.println("EnumSingleton 实例化");
        }

        public void doSomething() {
            System.out.println("EnumSingleton doSomething");
        }
    }

    public static void main(String[] args) {
        System.out.println("===== 1. 饿汉式 =====");
        System.out.println(EagerSingleton.getInstance() == EagerSingleton.getInstance());

        System.out.println("\n===== 2. 懒汉式（线程不安全） =====");
        System.out.println(LazySingletonUnsafe.getInstance() == LazySingletonUnsafe.getInstance());

        System.out.println("\n===== 3. 懒汉式（synchronized 方法） =====");
        System.out.println(LazySingletonSync.getInstance() == LazySingletonSync.getInstance());

        System.out.println("\n===== 4. 双重检查锁（DCL + volatile） =====");
        System.out.println(DCLSingleton.getInstance() == DCLSingleton.getInstance());

        System.out.println("\n===== 5. 静态内部类（Holder） =====");
        System.out.println(HolderSingleton.getInstance() == HolderSingleton.getInstance());

        System.out.println("\n===== 6. 枚举单例 =====");
        System.out.println(EnumSingleton.INSTANCE == EnumSingleton.INSTANCE);
        EnumSingleton.INSTANCE.doSomething();

        System.out.println("\n===== 多线程测试 DCL =====");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                DCLSingleton s = DCLSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " -> " + s.hashCode());
            }, "DCL-Thread-" + i).start();
        }
    }
}
