package com.java11.netty.binary;

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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @ClassName: BinaryClient
 * @Description: Netty自定义二进制协议客户端演示
 * @author liyuchang
 * @date 2026年9月5日
 */
public class BinaryClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8081;

    /**
     * 客户端业务处理器：打印服务端回执
     */
    static class ClientHandler extends SimpleChannelInboundHandler<NetMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, NetMessage msg) throws Exception {
            System.out.println("[BinaryClient] 收到服务端回执: " + msg.getContent());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Netty Binary Protocol Client Starting ===");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            int maxFrameLength = NetMessageDecoder.HEADER_LENGTH + NetMessageDecoder.MAX_BODY_LENGTH;
                            ch.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(
                                            maxFrameLength, 3, 4, 1, 0))
                                    .addLast(new NetMessageDecoder())
                                    .addLast(new NetMessageEncoder())
                                    .addLast(new ClientHandler());
                        }
                    });
            System.out.println("[BinaryClient] 连接服务器: " + HOST + ":" + PORT + " ...");
            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            System.out.println("[BinaryClient] 连接成功: " + future.channel().remoteAddress());
            Channel channel = future.channel();

            // 仿真粘包：连续快速发送多条消息，到达对端后可能被合并成一个 TCP 段
            System.out.println("--- 粘包场景：连续发送 5 条消息 ---");
            for (int i = 1; i <= 5; i++) {
                NetMessage message = new NetMessage(NetMessage.TYPE_REQUEST, "粘包测试消息-" + i);
                channel.writeAndFlush(message);
            }

            Thread.sleep(1000);

            // 仿真拆包：发送一条超长消息，超过 TCP MSS(约1460字节) 会被拆成多个 TCP 段
            System.out.println("--- 拆包场景：发送 1 条超长消息 ---");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 200; i++) {
                sb.append("消息").append(i).append(';');
            }
            NetMessage longMessage = new NetMessage(NetMessage.TYPE_REQUEST, sb.toString());
            System.out.println("[BinaryClient] 超长消息长度: " + sb.length() + " 字符");
            channel.writeAndFlush(longMessage);

            // 等待接收所有回执后关闭
            Thread.sleep(3000);
            System.out.println("[BinaryClient] 关闭连接...");
            channel.close().sync();
        } finally {
            group.shutdownGracefully();
            System.out.println("=== Netty Binary Protocol Client Shutdown ===");
        }
    }
}