package com.java11.design.strategy;

/**
 * 具体策略 - 支付宝支付
 */
public class AlipayStrategy implements PaymentStrategy {
    
    private String email;
    private String name;

    public AlipayStrategy(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @Override
    public void pay(double amount) {
        System.out.println("使用支付宝支付: " + amount + " 元");
        System.out.println("账户: " + email + ", 姓名: " + name);
        System.out.println("支付成功！\n");
    }
}
