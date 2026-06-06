package com.java11.design.strategy;

/**
 * 具体策略 - 信用卡支付
 */
public class CreditCardStrategy implements PaymentStrategy {
    
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private String name;

    public CreditCardStrategy(String cardNumber, String cvv, String expiryDate, String name) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.name = name;
    }

    @Override
    public void pay(double amount) {
        System.out.println("使用信用卡支付: " + amount + " 元");
        System.out.println("卡号: ****-****-****-" + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("持卡人: " + name);
        System.out.println("支付成功！\n");
    }
}
