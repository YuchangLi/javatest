package com.java11.design.responsibility;

/**
 * 具体处理者1 - 处理普通请求
 */
public class NormalHandler extends Handler {
    @Override
    public void handleRequest(Request request) {
        if ("normal".equals(request.getType())) {
            System.out.println("NormalHandler 处理普通请求: " + request.getContent());
            // 这里可以添加具体的业务逻辑
        } else if (nextHandler != null) {
            // 如果不能处理，传递给下一个处理者
            System.out.println("NormalHandler 无法处理该请求，传递给下一个处理者");
            nextHandler.handleRequest(request);
        } else {
            System.out.println("没有合适的处理者来处理请求: " + request);
        }
    }
}
