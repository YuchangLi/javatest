package com.java11.netty.binary;

/**
 * @ClassName: NetMessage
 * @Description: 自定义二进制协议消息实体
 * @author liyuchang
 * @date 2026年9月5日
 */
public class NetMessage {

    /** 请求消息类型 */
    public static final byte TYPE_REQUEST = 1;
    /** 响应消息类型 */
    public static final byte TYPE_RESPONSE = 2;

    /** 消息类型 */
    private byte type;
    /** 消息内容 */
    private String content;

    public NetMessage(byte type, String content) {
        this.type = type;
        this.content = content;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NetMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}