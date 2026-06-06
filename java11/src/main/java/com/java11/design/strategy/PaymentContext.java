package com.java11.design.strategy;

/**
 * 上下文类 - 维护对策略对象的引用
 * 
 * 这个类允许客户端在运行时切换不同的支付策略
 */
public class PaymentContext {
    
    private PaymentStrategy paymentStrategy;

    /**
     * 设置支付策略
     */
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    /**
     * 执行支付
     */
    public void executePayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("错误: 未设置支付策略！\n");
            return;
        }
        paymentStrategy.pay(amount);
    }
}
