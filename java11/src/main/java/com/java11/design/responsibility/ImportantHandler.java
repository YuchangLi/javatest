package com.java11.design.responsibility;

/**
 * 具体处理者2 - 处理重要请求
 */
public class ImportantHandler extends Handler {
    @Override
    public void handleRequest(Request request) {
        if ("important".equals(request.getType())) {
            System.out.println("ImportantHandler 处理重要请求: " + request.getContent());
            // 这里可以添加具体的业务逻辑
        } else if (nextHandler != null) {
            // 如果不能处理，传递给下一个处理者
            System.out.println("ImportantHandler 无法处理该请求，传递给下一个处理者");
            nextHandler.handleRequest(request);
        } else {
            System.out.println("没有合适的处理者来处理请求: " + request);
        }
    }
}
