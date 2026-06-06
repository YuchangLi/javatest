package com.java11.design.responsibility;

/**
 * 请求对象 - 封装需要处理的数据
 */
public class Request {
    private String type;        // 请求类型
    private String content;     // 请求内容
    private int priority;       // 优先级

    public Request(String type, String content, int priority) {
        this.type = type;
        this.content = content;
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", priority=" + priority +
                '}';
    }
}
