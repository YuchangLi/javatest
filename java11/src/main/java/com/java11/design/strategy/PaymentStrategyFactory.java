package com.java11.design.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentStrategyFactory {

    private static final Map<String, PaymentStrategy> CACHE = new ConcurrentHashMap<>();

    private PaymentStrategyFactory() {
    }

    public static AlipayStrategy getAlipayStrategy(String email, String name) {
        String key = "alipay:" + email + ":" + name;
        return (AlipayStrategy) CACHE.computeIfAbsent(key, k -> new AlipayStrategy(email, name));
    }

    public static WechatPayStrategy getWechatPayStrategy(String phoneNumber, String name) {
        String key = "wechat:" + phoneNumber + ":" + name;
        return (WechatPayStrategy) CACHE.computeIfAbsent(key, k -> new WechatPayStrategy(phoneNumber, name));
    }

    public static CreditCardStrategy getCreditCardStrategy(String cardNumber, String cvv, String expiryDate, String name) {
        String key = "credit:" + cardNumber + ":" + cvv + ":" + expiryDate + ":" + name;
        return (CreditCardStrategy) CACHE.computeIfAbsent(key, k -> new CreditCardStrategy(cardNumber, cvv, expiryDate, name));
    }
}
