package com.java11.design.responsibility;

/**
 * 具体处理者3 - 处理紧急请求
 */
public class UrgentHandler extends Handler {
    @Override
    public void handleRequest(Request request) {
        if ("urgent".equals(request.getType())) {
            System.out.println("UrgentHandler 处理紧急请求: " + request.getContent());
            // 这里可以添加具体的业务逻辑
        } else if (nextHandler != null) {
            // 如果不能处理，传递给下一个处理者
            System.out.println("UrgentHandler 无法处理该请求，传递给下一个处理者");
            nextHandler.handleRequest(request);
        } else {
            System.out.println("没有合适的处理者来处理请求: " + request);
        }
    }
}
