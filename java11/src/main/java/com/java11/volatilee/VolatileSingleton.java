package com.java11.volatilee;

/**
 * Singleton pattern implementation using volatile keyword
 * Double-checked locking singleton pattern
 */
public class VolatileSingleton {
    
    /**
     * Use volatile keyword to ensure visibility and ordering of instance variable
     * Volatile prevents instruction reordering, ensuring instance is fully initialized
     * before being accessed by other threads
     */
    private static volatile VolatileSingleton instance;
    
    /**
     * Private constructor to prevent external instantiation
     */
    private VolatileSingleton() {
        // Prevent instantiation via reflection
        if (instance != null) {
            throw new RuntimeException("Singleton instance already exists, do not create via reflection");
        }
        System.out.println(Thread.currentThread().getName() + " created singleton instance");
    }
    
    /**
     * Double-checked locking to get singleton instance
     * 1. First check if instance is null to avoid unnecessary synchronization
     * 2. Synchronized block to ensure thread safety in multi-thread environment
     * 3. Second check if instance is null to prevent multiple instances in multi-thread environment
     * @return VolatileSingleton instance
     */
    public static VolatileSingleton getInstance() {
        if (instance == null) { // First check
            synchronized (VolatileSingleton.class) { // Synchronized lock
                if (instance == null) { // Second check
                    instance = new VolatileSingleton();
                    // Without volatile, instruction reordering might occur here
                    // causing other threads to get uninitialized instance
                }
            }
        }
        return instance;
    }
    
    /**
     * Test method
     */
    public void doSomething() {
        System.out.println(Thread.currentThread().getName() + " using singleton instance");
    }
    
    /**
     * Main method to test thread safety of singleton pattern
     * @param args
     */
    public static void main(String[] args) {
        // Create multiple threads to get singleton instance simultaneously
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                VolatileSingleton singleton = VolatileSingleton.getInstance();
                singleton.doSomething();
            }, "Thread-" + i).start();
        }
    }
}