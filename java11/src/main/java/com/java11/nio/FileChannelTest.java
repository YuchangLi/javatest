package com.java11.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @ClassName: FileChannelTest
 * @Description: NIO文件通道演示 - 基本读写、通道间复制、零拷贝transferTo
 * @author liyuchang
 * @date 2026年9月5日
 */
public class FileChannelTest {

    private static final Path BASE = Paths.get("target", "nio-demo");

    public static void main(String[] args) throws IOException {
        Files.createDirectories(BASE);
        System.out.println("数据目录: " + BASE.toAbsolutePath());

        System.out.println("\n========== 测试1: FileChannel 基本读写 ==========\n");
        testBasicReadWrite();

        System.out.println("\n========== 测试2: 通道间复制(FileChannel.read/write) ==========\n");
        testChannelCopy();

        System.out.println("\n========== 测试3: 零拷贝 transferTo ==========\n");
        testTransferTo();
    }

    /**
     * 测试1：通过 ByteBuffer 向文件写入数据再读回
     */
    private static void testBasicReadWrite() throws IOException {
        Path file = BASE.resolve("basic.txt");
        String content = "Hello NIO FileChannel，测试中文内容！";

        try (FileChannel channel = FileChannel.open(file,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(content);
            int wrote = channel.write(buffer);
            System.out.println("写入字节数: " + wrote + ", 写入内容: " + content);
        }

        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            buffer.flip();
            String contentRead = StandardCharsets.UTF_8.decode(buffer).toString();
            System.out.println("读取字节数: " + read + ", 读取内容: " + contentRead);
            System.out.println("缓冲区位(position=" + buffer.position() + ", limit=" + buffer.limit() + ", capacity=" + buffer.capacity() + ")");
        }
    }

    /**
     * 测试2：通过 ByteBuffer 在两个通道间复制数据（模拟粘包式小缓冲分块搬运）
     */
    private static void testChannelCopy() throws IOException {
        Path src = BASE.resolve("src-copy.txt");
        Path dst = BASE.resolve("dst-copy.txt");
        Files.writeString(src, "通过 FileChannel.read/write 分块复制的数据，验证多个通道共享 ByteBuffer");

        try (FileChannel in = FileChannel.open(src, StandardOpenOption.READ);
             FileChannel out = FileChannel.open(dst,
                     StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            // 刻意用小缓冲，触发多次cycle，直观展示 position/limit 翻转
            ByteBuffer buffer = ByteBuffer.allocate(16);
            int cycles = 0;
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    out.write(buffer);
                }
                buffer.clear();
                cycles++;
            }
            System.out.println("分块复制循环次数: " + cycles);
        }
        System.out.println("复制结果: " + Files.readString(dst));
    }

    /**
     * 测试3：transferTo 零拷贝，数据不经过 Java 堆直接在内核态搬运
     */
    private static void testTransferTo() throws IOException {
        Path src = BASE.resolve("src-zero-copy.txt");
        Path dst = BASE.resolve("dst-zero-copy.txt");
        Files.writeString(src, "零拷贝 transferTo 数据，用于演示内核态直接搬运。" + "x".repeat(1000));

        long size = Files.size(src);
        try (FileChannel in = FileChannel.open(src, StandardOpenOption.READ);
             FileChannel out = FileChannel.open(dst,
                     StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            long copied = 0;
            while (copied < size) {
                copied += in.transferTo(copied, size - copied, out);
            }
            System.out.println("源文件大小: " + size + " 字节, 零拷贝复制字节数: " + copied);
        }
        System.out.println("复制结果长度: " + Files.readString(dst).length() + " 字符");
    }
}