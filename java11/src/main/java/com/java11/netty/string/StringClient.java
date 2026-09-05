package com.java11.netty.string;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: StringClient
 * @Description: Netty字符串协议客户端演示 - 连接服务端并发送按行分隔的消息
 * @author liyuchang
 * @date 2026年9月5日
 */
public class StringClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    private static final int MAX_LINE_LENGTH = 1024;

    /**
     * 客户端业务处理器：打印服务端回复
     */
    static class ClientHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("[Client] 收到服务端回复: " + msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Netty String Protocol Client Starting ===");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LineBasedFrameDecoder(MAX_LINE_LENGTH))
                                    .addLast(new StringDecoder(StandardCharsets.UTF_8))
                                    .addLast(new StringEncoder(StandardCharsets.UTF_8))
                                    .addLast(new ClientHandler());
                        }
                    });
            System.out.println("[Client] 连接服务器: " + HOST + ":" + PORT + " ...");
            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            System.out.println("[Client] 连接成功: " + future.channel().remoteAddress());
            Channel channel = future.channel();

            // 连续发送多行消息，每行以 \n 结尾
            String[] messages = {
                    "Hello Netty!",
                    "这是字符串协议演示",
                    "服务器是不是能收到呀"
            };
            for (String message : messages) {
                System.out.println("[Client] 发送消息: " + message);
                channel.writeAndFlush(message + System.lineSeparator());
                Thread.sleep(500);
            }

            // 等待一段时间接收所有回复后关闭
            Thread.sleep(2000);
            System.out.println("[Client] 关闭连接...");
            channel.close().sync();
        } finally {
            group.shutdownGracefully();
            System.out.println("=== Netty String Protocol Client Shutdown ===");
        }
    }
}