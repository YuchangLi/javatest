package com.java11.classLoader;

import io.reactivex.CompletableConverter;
import io.reactivex.observers.TestObserver;

public class ArraysClassLoaderTest {
    public static void main(String[] args) {
        int[] a = new int[10];
        System.out.println(a.getClass().getClassLoader());

        Integer[] b = new Integer[10];
        System.out.println(b.getClass().getClassLoader());

        String[] s = new String[10];
        System.out.println(s.getClass().getClassLoader());

        ArraysClassLoaderTest[] c = new ArraysClassLoaderTest[10];
        System.out.println(c.getClass().getClassLoader());

        CompletableConverter[] d = new CompletableConverter[10];
        System.out.println(d.getClass().getClassLoader());

        TestObserver[] e = new TestObserver[10];
        System.out.println(e.getClass().getClassLoader());
        
        // 展示不同类加载器的层次结构
        ClassLoader appClassLoader = ArraysClassLoaderTest.class.getClassLoader();
        System.out.println("\nApplication ClassLoader: " + appClassLoader);
        
        ClassLoader platformClassLoader = appClassLoader.getParent();
        System.out.println("Platform ClassLoader (formerly Extension): " + platformClassLoader);
        
        ClassLoader bootstrapClassLoader = platformClassLoader.getParent();
        System.out.println("Bootstrap ClassLoader: " + bootstrapClassLoader);
        
        // 尝试获取一个由平台类加载器加载的类的数组
        try {
            Class<?> xmlClass = Class.forName("javax.xml.parsers.DocumentBuilder");
            Object xmlArray = java.lang.reflect.Array.newInstance(xmlClass, 10);
            System.out.println("javax.xml.parsers.DocumentBuilder[] class loader: " + 
                xmlArray.getClass().getClassLoader());
        } catch (ClassNotFoundException ex) {
            System.out.println("Could not find class for platform classloader demo");
        }
    }
}
