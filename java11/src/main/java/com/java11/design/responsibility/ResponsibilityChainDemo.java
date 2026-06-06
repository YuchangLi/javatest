package com.java11.design.responsibility;

/**
 * 责任链模式演示类
 * 
 * 责任链模式是一种行为设计模式，允许你将请求沿着处理者链传递，
 * 直到有一个处理者能够处理它为止。
 */
public class ResponsibilityChainDemo {
    
    public static void main(String[] args) {
        // 创建处理者实例
        Handler normalHandler = new NormalHandler();
        Handler importantHandler = new ImportantHandler();
        Handler urgentHandler = new UrgentHandler();
        
        // 构建责任链: normal -> important -> urgent
        normalHandler.setNextHandler(importantHandler);
        importantHandler.setNextHandler(urgentHandler);
        
        System.out.println("========== 责任链模式演示 ==========\n");
        
        // 测试1: 普通请求
        System.out.println("测试1: 发送普通请求");
        Request normalRequest = new Request("normal", "这是一条普通消息", 1);
        normalHandler.handleRequest(normalRequest);
        System.out.println();
        
        // 测试2: 重要请求
        System.out.println("测试2: 发送重要请求");
        Request importantRequest = new Request("important", "这是一条重要消息", 2);
        normalHandler.handleRequest(importantRequest);
        System.out.println();
        
        // 测试3: 紧急请求
        System.out.println("测试3: 发送紧急请求");
        Request urgentRequest = new Request("urgent", "这是一条紧急消息", 3);
        normalHandler.handleRequest(urgentRequest);
        System.out.println();
        
        // 测试4: 未知类型请求
        System.out.println("测试4: 发送未知类型请求");
        Request unknownRequest = new Request("unknown", "这是一条未知类型消息", 0);
        normalHandler.handleRequest(unknownRequest);
        System.out.println();
        
        // 测试5: 从不同节点开始处理
        System.out.println("测试5: 从ImportantHandler开始处理紧急请求");
        importantHandler.handleRequest(urgentRequest);
        System.out.println();
        
        System.out.println("========== 演示结束 ==========");
    }
}
