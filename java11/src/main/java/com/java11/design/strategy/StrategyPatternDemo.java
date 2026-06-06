package com.java11.design.strategy;

/**
 * 策略模式演示类
 * 
 * 策略模式是一种行为设计模式，它定义了一系列算法，并将每个算法封装起来，
 * 使它们可以相互替换。策略模式让算法的变化独立于使用算法的客户端。
 * 
 * 主要角色：
 * 1. Strategy（策略接口）：定义所有支持算法的公共接口 - PaymentStrategy
 * 2. ConcreteStrategy（具体策略）：实现Strategy接口的具体算法 - AlipayStrategy, WechatPayStrategy等
 * 3. Context（上下文）：维护对Strategy对象的引用 - PaymentContext
 * 
 * 优点：
 * - 算法可以自由切换
 * - 避免使用多重条件判断
 * - 扩展性良好，新增策略只需实现Strategy接口
 * 
 * 适用场景：
 * - 多个类只有算法或行为不同
 * - 需要在运行时选择具体的算法
 * - 需要屏蔽算法规则的细节
 */
public class StrategyPatternDemo {
    
    public static void main(String[] args) {
        System.out.println("========== 策略模式演示 ==========\n");
        
        // 创建支付上下文
        PaymentContext paymentContext = new PaymentContext();
        
        // 测试1: 使用支付宝支付
        System.out.println("测试1: 使用支付宝支付");
        PaymentStrategy alipay = PaymentStrategyFactory.getAlipayStrategy("user@example.com", "张三");
        paymentContext.setPaymentStrategy(alipay);
        paymentContext.executePayment(100.50);
        
        // 测试2: 使用微信支付
        System.out.println("测试2: 使用微信支付");
        PaymentStrategy wechatPay = PaymentStrategyFactory.getWechatPayStrategy("13800138000", "李四");
        paymentContext.setPaymentStrategy(wechatPay);
        paymentContext.executePayment(200.00);
        
        // 测试3: 使用信用卡支付
        System.out.println("测试3: 使用信用卡支付");
        PaymentStrategy creditCard = PaymentStrategyFactory.getCreditCardStrategy("1234567890123456", "123", "12/25", "王五");
        paymentContext.setPaymentStrategy(creditCard);
        paymentContext.executePayment(500.75);
        
        // 测试4: 动态切换策略 - 同一用户选择不同的支付方式
        System.out.println("测试4: 动态切换支付策略（工厂缓存实例复用）");
        System.out.println("第一次购买 - 使用支付宝:");
        PaymentStrategy sameAlipay = PaymentStrategyFactory.getAlipayStrategy("user@example.com", "张三");
        System.out.println("   实例复用? " + (alipay == sameAlipay));
        paymentContext.setPaymentStrategy(sameAlipay);
        paymentContext.executePayment(50.00);
        
        System.out.println("第二次购买 - 切换为微信支付:");
        paymentContext.setPaymentStrategy(PaymentStrategyFactory.getWechatPayStrategy("13800138000", "张三"));
        paymentContext.executePayment(80.00);
        
        System.out.println("========== 演示结束 ==========");
    }
}
