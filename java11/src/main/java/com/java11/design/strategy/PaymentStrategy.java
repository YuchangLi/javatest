package com.java11.design.strategy;

/**
 * 策略接口 - 定义所有支持算法的公共接口
 */
public interface PaymentStrategy {
    /**
     * 支付方法
     * @param amount 支付金额
     */
    void pay(double amount);
}
