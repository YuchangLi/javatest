package com.java11.design.strategy;

/**
 * 具体策略 - 微信支付
 */
public class WechatPayStrategy implements PaymentStrategy {
    
    private String phoneNumber;
    private String name;

    public WechatPayStrategy(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    @Override
    public void pay(double amount) {
        System.out.println("使用微信支付: " + amount + " 元");
        System.out.println("手机号: " + phoneNumber + ", 姓名: " + name);
        System.out.println("支付成功！\n");
    }
}
