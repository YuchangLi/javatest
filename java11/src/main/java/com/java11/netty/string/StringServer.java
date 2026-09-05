package com.java11.netty.string;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: StringServer
 * @Description: Netty字符串协议服务端演示 - 使用 LineBasedFrameDecoder 按行分隔消息
 * @author liyuchang
 * @date 2026年9月5日
 */
public class StringServer {

    private static final int PORT = 8080;
    private static final int MAX_LINE_LENGTH = 1024;

    /**
     * 服务端业务处理器：收到一行消息后打印，并回复
     */
    static class ServerHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("[Server] 收到消息: " + msg);
            String reply = "Hello from Server, 收到你的消息: " + msg;
            ctx.writeAndFlush(reply + System.lineSeparator());
            System.out.println("[Server] 已回复: " + reply);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("[Server] 客户端连接: " + ctx.channel().remoteAddress());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("[Server] 客户端断开: " + ctx.channel().remoteAddress());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Netty String Protocol Server Starting ===");
        // bossGroup 处理 accept，workerGroup 处理读写
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 以 \n 为分隔符解码，防止半包消息
                            ch.pipeline()
                                    .addLast(new LineBasedFrameDecoder(MAX_LINE_LENGTH))
                                    .addLast(new StringDecoder(StandardCharsets.UTF_8))
                                    .addLast(new StringEncoder(StandardCharsets.UTF_8))
                                    .addLast(new ServerHandler());
                        }
                    });
            System.out.println("[Server] 监听端口: " + PORT);
            ChannelFuture future = bootstrap.bind(PORT).sync();
            System.out.println("[Server] 启动成功，等待客户端连接...");
            // 等待服务端通道关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("=== Netty String Protocol Server Shutdown ===");
        }
    }
}