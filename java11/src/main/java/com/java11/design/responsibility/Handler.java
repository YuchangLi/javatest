package com.java11.design.responsibility;

/**
 * 抽象处理者 - 定义处理请求的接口和维持下一个处理者的引用
 */
public abstract class Handler {
    protected Handler nextHandler;

    /**
     * 设置下一个处理者
     */
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * 处理请求的方法
     */
    public abstract void handleRequest(Request request);
}
