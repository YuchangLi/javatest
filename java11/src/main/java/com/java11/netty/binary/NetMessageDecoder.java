package com.java11.netty.binary;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName: NetMessageDecoder
 * @Description: 自定义二进制协议解码器 - 校验魔数并解析消息体
 * @author liyuchang
 * @date 2026年9月5日
 */
public class NetMessageDecoder extends ByteToMessageDecoder {

    /** 魔数，用于协议识别和粘包过滤 */
    public static final short MAGIC_NUMBER = 0x5A5A;
    /** 协议版本 */
    public static final byte VERSION = 0x01;
    /** 头部长度：魔数(2) + 版本(1) + 长度(4) + 类型(1) */
    public static final int HEADER_LENGTH = 8;
    /** 消息体最大长度 */
    public static final int MAX_BODY_LENGTH = 4096;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < HEADER_LENGTH) {
            return;
        }
        in.markReaderIndex();
        short magic = in.readShort();
        if (magic != MAGIC_NUMBER) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("非法的魔数: 0x" + Integer.toHexString(magic & 0xFFFF));
        }
        byte version = in.readByte();
        if (version != VERSION) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("不支持的协议版本: " + version);
        }
        int length = in.readInt();
        if (length < 0 || length > MAX_BODY_LENGTH) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("非法的消息体长度: " + length);
        }
        byte type = in.readByte();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] body = new byte[length];
        in.readBytes(body);
        String content = new String(body, StandardCharsets.UTF_8);
        System.out.println("[NetMessageDecoder] 解码出一条完整消息: type=" + type + ", content=[" + content + "]");
        out.add(new NetMessage(type, content));
    }
}