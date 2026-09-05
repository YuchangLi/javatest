package com.java11.netty.binary;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: NetMessageEncoder
 * @Description: 自定义二进制协议编码器 - 将消息实体编码为字节流
 * @author liyuchang
 * @date 2026年9月5日
 */
public class NetMessageEncoder extends MessageToByteEncoder<NetMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, NetMessage msg, ByteBuf out) throws Exception {
        byte[] body = msg.getContent().getBytes(StandardCharsets.UTF_8);
        // 魔数(2) + 版本(1) + 长度(4) + 类型(1) + 消息体(可变)
        out.writeShort(NetMessageDecoder.MAGIC_NUMBER);
        out.writeByte(NetMessageDecoder.VERSION);
        out.writeInt(body.length);
        out.writeByte(msg.getType());
        out.writeBytes(body);
        System.out.println("[NetMessageEncoder] 编码一条消息: type=" + msg.getType() + ", bodyLength=" + body.length);
    }
}