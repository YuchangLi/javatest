package com.java11.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: NioServer
 * @Description: NIO多路复用服务端演示 - 单线程Selector同时处理多个客户端连接与读写
 * @author liyuchang
 * @date 2026年9月5日
 */
public class NioServer {

    private static final int PORT = 8081;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        // 打开多路复用器
        Selector selector = Selector.open();
        // 打开服务端通道并设置非阻塞
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(PORT));
        // 注册 accept 事件到 Selector
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("[Server] NIO 多路复用服务端启动，监听端口: " + PORT);
        System.out.println();
        AtomicInteger clientCount = new AtomicInteger();
        while (true) {
            // 阻塞等待至少一个就绪的事件
            selector.select();
            // 获取就绪事件集合
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    // 有新的客户端连接
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("[Server] 客户端连接 #" + clientCount.incrementAndGet() + ": " + client.getRemoteAddress());
                    System.out.println();
                } else if (key.isReadable()) {
                    // 有客户端发送数据
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                    int read = client.read(buffer);
                    if (read == -1) {
                        String addr = String.valueOf(client.getRemoteAddress());
                        client.close();
                        System.out.println("[Server] 客户端断开: " + addr);
                        continue;
                    }
                    buffer.flip();
                    String msg = StandardCharsets.UTF_8.decode(buffer).toString().trim();
                    System.out.println("[Server] 收到来自 " + client.getRemoteAddress() + " 的消息: " + msg);
                    String reply = "Hello from NIO Server, 收到你的消息: " + msg;
                    client.write(StandardCharsets.UTF_8.encode(reply + System.lineSeparator()));
                    System.out.println("[Server] 已回复: " + reply);
                    System.out.println();
                }
            }
        }
    }
}