package com.java11.netty.binary;

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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: BinaryServer
 * @Description: Netty自定义二进制协议服务端演示 - 处理粘包/拆包
 * @author liyuchang
 * @date 2026年9月5日
 */
public class BinaryServer {

    private static final int PORT = 8081;

    /**
     * 服务端业务处理器：收到解码后的消息，打印并回执
     */
    static class ServerHandler extends SimpleChannelInboundHandler<NetMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, NetMessage msg) throws Exception {
            System.out.println("[BinaryServer] 收到消息: " + msg);
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            NetMessage reply = new NetMessage(NetMessage.TYPE_RESPONSE,
                    "服务端 " + time + " 已收到你的消息: " + msg.getContent());
            ctx.writeAndFlush(reply);
            System.out.println("[BinaryServer] 已回执: " + reply);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("[BinaryServer] 客户端连接: " + ctx.channel().remoteAddress());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("[BinaryServer] 客户端断开: " + ctx.channel().remoteAddress());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Netty Binary Protocol Server Starting ===");
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
                            int maxFrameLength = NetMessageDecoder.HEADER_LENGTH + NetMessageDecoder.MAX_BODY_LENGTH;
                            ch.pipeline()
                                    // 帧解码器：按长度字段切分完整帧，解决粘包/拆包
                                    .addLast(new LengthFieldBasedFrameDecoder(
                                            maxFrameLength, 3, 4, 1, 0))
                                    .addLast(new NetMessageDecoder())
                                    .addLast(new NetMessageEncoder())
                                    .addLast(new ServerHandler());
                        }
                    });
            System.out.println("[BinaryServer] 监听端口: " + PORT);
            ChannelFuture future = bootstrap.bind(PORT).sync();
            System.out.println("[BinaryServer] 启动成功，等待客户端连接...");
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("=== Netty Binary Protocol Server Shutdown ===");
        }
    }
}