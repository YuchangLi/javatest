package com.jvm.demo;

import sun.awt.windows.ThemeReader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    private final static Map<String, String> map = new ConcurrentHashMap<String, String>();

    private static String put(String key, String value) {
//        if (map.get(key) != null) {
//            System.out.println("existed");
//            throw  new RuntimeException(key+" existed!");
//        }
//        System.out.println(Thread.currentThread().getId()+" put "+ key +" success");
//        map.put(key, value);
//        return map.computeIfAbsent(key, t -> value+"1");
        return map.putIfAbsent(key, value);
    }

    public static void main(String[] args) throws InterruptedException {
        String key = "k1";
        Thread t1 = new Thread(() -> System.out.println("put(\"k1\", \"v1\") = "+put(key, "v1")));
        Thread t2 = new Thread(() -> System.out.println("put(\"k1\", \"v2\") = "+put(key, "v2")));
        t1.start();
        t2.start();
        Thread.sleep(100);
        System.out.println("map.get(\"k1\") = " + map.get(key));
    }
}
