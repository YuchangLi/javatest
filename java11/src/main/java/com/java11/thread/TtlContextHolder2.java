package com.java11.thread;


import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TtlContextHolder2 {

    // 1. 使用 static final 确保 TTL 实例不被重复创建，防止内存泄漏
    // 重写 copy 方法（可选）：如果是引用类型，建议实现深拷贝
    private static final TransmittableThreadLocal<Map> TTL_CONTEXT = new TransmittableThreadLocal<>() {
        @Override
        public Map copy(Map parentValue) {
            // 默认是直接返回引用，如果是可变对象（如 Map），请在这里 new 新对象
            return new HashMap(parentValue);
        }
    };

    // 2. 线程池初始化:确保只被 TtlExecutors 包装一次
    private static final ExecutorService TTL_EXECUTOR_SERVICE;

    static {
        ExecutorService rawExecutor = new ThreadPoolExecutor(
                2, 4, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                r -> new Thread(r, "ttl-worker-" + System.nanoTime()),
                new ThreadPoolExecutor.CallerRunsPolicy() // 关键:TTL 完美支持此拒绝策略
        );
        // 包装原始线程池
        TTL_EXECUTOR_SERVICE = TtlExecutors.getTtlExecutorService(rawExecutor);
    }

    public static void main(String[] args) throws Exception {

        System.out.println("\n========== 测试5: TTL 支持异步嵌套传递 ==========\n");
        testNestedAsync();

        // 关闭线程池
        TTL_EXECUTOR_SERVICE.shutdown();
    }

    /**
     * 演示 TTL 支持异步嵌套场景: 父线程 -> 子线程 -> 孙线程
     */
    private static void testNestedAsync() throws Exception {
        TTL_CONTEXT.set(new HashMap(Map.of("parent-context", "parent-context")));
        log.info("[主线程] 设置上下文: {}", TTL_CONTEXT.get());

        // 第一层异步
        TTL_EXECUTOR_SERVICE.submit(() -> {
            Map parMap = TTL_CONTEXT.get();
            log.info("[子线程-1] 读取上下文: {}", parMap);
            parMap.put("parent-context2", "parent-context2");

            // 在子线程中修改上下文
            TTL_CONTEXT.set(Map.of("child-context", "child-context"));
            log.info("[子线程-1] 修改上下文为: {}", TTL_CONTEXT.get());

            // 第二层异步: 子线程提交任务到线程池
            try {
                TTL_EXECUTOR_SERVICE.submit(() -> {
                    log.info("[孙线程-2] 读取上下文: {}", TTL_CONTEXT.get());

                    // 再次修改
                    TTL_CONTEXT.set(Map.of("grandchild-context", "grandchild-context"));
                    log.info("[孙线程-2] 修改上下文为: {}", TTL_CONTEXT.get());
                }).get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // 验证子线程的上下文是否被孙线程污染
            log.info("[子线程-1] 孙线程执行后，当前上下文: {}", TTL_CONTEXT.get());
        }).get();

        // 验证主线程的上下文是否被子线程污染
        log.info("[主线程] 子线程执行后，当前上下文: {}", TTL_CONTEXT.get());
        log.info("\n说明: TTL 通过 copy/restore 机制实现了上下文隔离，避免污染");
    }
}