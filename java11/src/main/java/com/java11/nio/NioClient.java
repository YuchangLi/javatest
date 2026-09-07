package com.java11.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: NioClient
 * @Description: NIO非阻塞客户端演示 - 连接服务端并轮询发送/接收消息(请先启动NioServer)
 * @author liyuchang
 * @date 2026年9月5日
 */
public class NioClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8081;

    public static void main(String[] args) throws Exception {
        // 打开客户端通道并设置非阻塞
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        // 发起非阻塞连接，未完成时轮询等待
        if (!channel.connect(new InetSocketAddress(HOST, PORT))) {
            System.out.println("[Client] 连接中...");
            while (!channel.finishConnect()) {
                Thread.sleep(100);
            }
        }
        System.out.println("[Client] 已连接到服务端: " + channel.getRemoteAddress());
        System.out.println();
        Thread.sleep(5000);

        for (int i = 1; i <= 3; i++) {
            String text = "Hello NIO, 第 " + i + " 条消息";
            channel.write(StandardCharsets.UTF_8.encode(text));
            System.out.println("[Client] 发送: " + text);

            // 轮询读取服务端回显
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder received = new StringBuilder();
            long deadline = System.currentTimeMillis() + 3000;
            while (System.currentTimeMillis() < deadline) {
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    received.append(StandardCharsets.UTF_8.decode(buffer));
                    buffer.clear();
                    break;
                }
                Thread.sleep(50);
            }
            if (received.length() > 0) {
                System.out.println("[Client] 收到回显: " + received.toString().trim());
            } else {
                System.out.println("[Client] 未收到回显");
            }
            Thread.sleep(500);
            System.out.println();
        }

        channel.close();
        System.out.println("[Client] 连接已关闭");
    }
}