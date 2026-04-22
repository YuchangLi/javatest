package com.java11.thread;



import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class TtlContextHolder {

    // 1. 使用 static final 确保 TTL 实例不被重复创建，防止内存泄漏
    // 重写 copy 方法（可选）：如果是引用类型，建议实现深拷贝
    private static final TransmittableThreadLocal<String> TTL_CONTEXT = new TransmittableThreadLocal<>() {
        @Override
        public String copy(String parentValue) {
            // 默认是直接返回引用，如果是可变对象（如 Map），请在这里 new 新对象
            return parentValue;
        }
    };

    // InheritableThreadLocal 用于对比
    private static final InheritableThreadLocal<String> ITL_CONTEXT = new InheritableThreadLocal<>();

    // 普通 ThreadLocal 用于对比
    private static final ThreadLocal<String> NORMAL_CONTEXT = new ThreadLocal<>();

    // 2. 线程池初始化:确保只被 TtlExecutors 包装一次
    private static final ExecutorService TTL_EXECUTOR_SERVICE;
    private static final ExecutorService RAW_EXECUTOR_SERVICE;
    
    static {
        ExecutorService rawExecutor = new ThreadPoolExecutor(
                1, 4, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                r -> new Thread(r, "ttl-worker-" + System.nanoTime()),
                new ThreadPoolExecutor.CallerRunsPolicy() // 关键:TTL 完美支持此拒绝策略
        );
        // 包装原始线程池
        TTL_EXECUTOR_SERVICE = TtlExecutors.getTtlExecutorService(rawExecutor);
        // 保留一个未包装的线程池用于对比
        RAW_EXECUTOR_SERVICE = new ThreadPoolExecutor(
                1, 4, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                r -> new Thread(r, "raw-worker-" + System.nanoTime()),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    public static void main(String[] args) throws Exception {
//        System.out.println("========== 测试1: 三种 ThreadLocal 在线程池中的对比 ==========\n");
//        testThreeThreadLocalsInThreadPool();

//        System.out.println("\n========== 测试2: 三种 ThreadLocal 在手动创建线程中的对比 ==========\n");
//        testThreeThreadLocalsInManualThread();
//
//        System.out.println("\n========== 测试3: TTL 在任务提交时捕获上下文(核心特性) ==========\n");
//        testCaptureAtSubmitTime();

        System.out.println("\n========== 测试4: 父线程修改上下文，已运行的子线程能否感知? ==========\n");
        testModifyContextWithoutResubmit();

//        System.out.println("\n========== 测试5: TTL 支持异步嵌套传递 ==========\n");
//        testNestedAsync();

        // 关闭线程池
        TTL_EXECUTOR_SERVICE.shutdown();
        RAW_EXECUTOR_SERVICE.shutdown();
    }
    
    /**
     * 对比三种 ThreadLocal 在线程池中的表现
     */
    private static void testThreeThreadLocalsInThreadPool() throws Exception {
        // 设置上下文
        NORMAL_CONTEXT.set("normal-context");
        ITL_CONTEXT.set("itl-context");
        TTL_CONTEXT.set("ttl-context");

        log.info("[主线程] NormalContext: {}", NORMAL_CONTEXT.get());
        log.info("[主线程] ITLContext: {}", ITL_CONTEXT.get());
        log.info("[主线程] TTLContext: {}", TTL_CONTEXT.get());

        // 提交到普通线程池（未包装）
        log.info("\n--- 提交到普通线程池（未包装）---");
        RAW_EXECUTOR_SERVICE.submit(() -> {
            log.info("[普通线程池] NormalContext: {}", NORMAL_CONTEXT.get());
            log.info("[普通线程池] ITLContext: {}", ITL_CONTEXT.get());
            log.info("[普通线程池] TTLContext: {}", TTL_CONTEXT.get());
        }).get();

        // 提交到 TTL 包装的线程池
        log.info("\n--- 提交到 TTL 包装的线程池---");
        TTL_EXECUTOR_SERVICE.submit(() -> {
            log.info("[TTL线程池] NormalContext: {}", NORMAL_CONTEXT.get());
            log.info("[TTL线程池] ITLContext: {}", ITL_CONTEXT.get());
            log.info("[TTL线程池] TTLContext: {}", TTL_CONTEXT.get());
        }).get();

        log.info("\n说明: ");
        log.info("  - ThreadLocal: 无法传递到线程池中的子线程");
        log.info("  - InheritableThreadLocal: 只在第一次线程创建时传递，线程池复用导致后续无法传递");
        log.info("  - TransmittableThreadLocal: 通过包装线程池，每次 submit 都能正确传递");
    }

    /**
     * 对比三种 ThreadLocal 在手动创建线程中的表现
     */
    private static void testThreeThreadLocalsInManualThread() throws Exception {
        // 设置上下文
        NORMAL_CONTEXT.set("normal-manual");
        ITL_CONTEXT.set("itl-manual");
        TTL_CONTEXT.set("ttl-manual");

        log.info("[主线程] NormalContext: {}", NORMAL_CONTEXT.get());
        log.info("[主线程] ITLContext: {}", ITL_CONTEXT.get());
        log.info("[主线程] TTLContext: {}", TTL_CONTEXT.get());

        // 手动创建新线程（不使用线程池）
        Thread thread = new Thread(() -> {
            log.info("[手动创建线程] NormalContext: {}", NORMAL_CONTEXT.get());
            log.info("[手动创建线程] ITLContext: {}", ITL_CONTEXT.get());
            log.info("[手动创建线程] TTLContext: {}", TTL_CONTEXT.get());
        });
        thread.start();
        thread.join();

        log.info("\n说明: ");
        log.info("  - ThreadLocal: 无法传递到新线程");
        log.info("  - InheritableThreadLocal: 可以传递到新线程（线程创建时继承）");
        log.info("  - TransmittableThreadLocal: 需要配合 TtlExecutors 使用，直接 new Thread 不会自动传递");
    }
    
    /**
     * 演示 TTL 的核心特性: 在任务提交时捕获上下文，而非线程创建时
     * 这是与普通 InheritableThreadLocal 的本质区别
     */
    private static void testCaptureAtSubmitTime() throws Exception {
        log.info("[主线程] 初始状态，未设置上下文");

        // 第一次提交: 此时没有设置上下文
        log.info("\n--- 第1次提交（无上下文）---");
        Future<?> future1 = TTL_EXECUTOR_SERVICE.submit(() -> {
            log.info("[任务1] TTLContext: {}", TTL_CONTEXT.get());
        });
        future1.get();

        ITL_CONTEXT.set("itl-context-captured");
        Future<?> future11 = RAW_EXECUTOR_SERVICE.submit(() -> {
            log.info("[任务11] ITL_CONTEXT: {}", ITL_CONTEXT.get());
        });
        future11.get();

        // 设置上下文
        TTL_CONTEXT.set("ttl-context-captured");
        log.info("\n[主线程] 设置上下文为: ttl-context-captured");

        // 第二次提交: 此时有上下文，TTL 会在提交时捕获
        log.info("\n--- 第2次提交（有上下文）---");
        Future<?> future2 = TTL_EXECUTOR_SERVICE.submit(() -> {
            log.info("[任务2] TTLContext: {}", TTL_CONTEXT.get());
        });
        future2.get();
        Future<?> future22 = RAW_EXECUTOR_SERVICE.submit(() -> {
            log.info("[任务22] ITL_CONTEXT: {}", ITL_CONTEXT.get());
        });
        future22.get();

        // 修改上下文
        TTL_CONTEXT.set("ttl-context-modified");
        ITL_CONTEXT.set("itl-context-modified");
        log.info("\n[主线程] 修改上下文为: ttl-context-modified itl-context-modified");

        // 第三次提交: 会捕获新的上下文
        log.info("\n--- 第3次提交（修改后的上下文）---");
        Future<?> future3 = TTL_EXECUTOR_SERVICE.submit(() -> {
            log.info("[任务3] TTLContext: {}", TTL_CONTEXT.get());
        });
        future3.get();
        Future<?> future33 = RAW_EXECUTOR_SERVICE.submit(() -> {
            log.info("[任务33] ITL_CONTEXT: {}", ITL_CONTEXT.get());
        });
        future33.get();

        log.info("\n说明: TTL 在每次 submit 时捕获当前上下文，实现按需传递，而不是在线程创建时一次性传递, ITL修改不能传递到已存在的子线程中");
    }

    /**
     * 关键问题: 父线程修改上下文后，不重新提交任务，已经在运行的子线程能否看到修改?
     * 答案: 不能！因为 TTL 在 submit 时就捕获了快照，子线程运行的是捕获的快照副本
     */
    private static void testModifyContextWithoutResubmit() throws Exception {
        // 设置初始上下文
        TTL_CONTEXT.set("initial-value");
        ITL_CONTEXT.set("itl-initial-value");
        log.info("[主线程] 设置初始上下文: TTL={}, ITL={}", TTL_CONTEXT.get(), ITL_CONTEXT.get());

        // 提交一个长时间运行的任务到 TTL 线程池
        log.info("\n--- 提交长时任务到 TTL 线程池 ---");
        Future<?> ttlFuture = TTL_EXECUTOR_SERVICE.submit(() -> {
            log.info("[TTL子线程] 开始执行，读取上下文: {}", TTL_CONTEXT.get());

            // 模拟业务处理，等待父线程修改上下文
            try {
                log.info("[TTL子线程] 等待 2 秒，让父线程修改上下文...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // 再次读取上下文
            log.info("[TTL子线程] 等待后再次读取上下文: {}", TTL_CONTEXT.get());
            log.info("[TTL子线程] 是否能读到父线程的修改? {}", 
                    "initial-value".equals(TTL_CONTEXT.get()) ? "❌ 不能，还是旧值" : "✅ 能，读到新值");
        });

        // 提交一个长时间运行的任务到普通线程池（ITL）
        log.info("\n--- 提交长时任务到普通线程池(ITL) ---");
        Future<?> itlFuture = RAW_EXECUTOR_SERVICE.submit(() -> {
            log.info("[ITL子线程] 开始执行，读取上下文: {}", ITL_CONTEXT.get());

            // 模拟业务处理，等待父线程修改上下文
            try {
                log.info("[ITL子线程] 等待 2 秒，让父线程修改上下文...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // 再次读取上下文
            log.info("[ITL子线程] 等待后再次读取上下文: {}", ITL_CONTEXT.get());
            log.info("[ITL子线程] 是否能读到父线程的修改? {}", 
                    "itl-initial-value".equals(ITL_CONTEXT.get()) ? "❌ 不能，还是旧值" : "✅ 能，读到新值");
        });

        // 等待子线程启动并读取初始值
        Thread.sleep(500);

        // 父线程修改上下文
        log.info("\n[主线程] 修改上下文为: ttl-modified-value 和 itl-modified-value");
        TTL_CONTEXT.set("ttl-modified-value");
        ITL_CONTEXT.set("itl-modified-value");
        log.info("[主线程] 修改完成，当前上下文: TTL={}, ITL={}", TTL_CONTEXT.get(), ITL_CONTEXT.get());

        // 等待子线程执行完毕
        ttlFuture.get();
        itlFuture.get();

        log.info("\n结论:");
        log.info("  ❌ 子线程无法感知父线程的修改，因为 TTL/ITL 在传递时是值拷贝（快照）");
        log.info("  📌 TTL 在 submit() 时捕获快照，子线程操作的是副本");
        log.info("  📌 ITL 在线程创建时继承值，也是副本");
        log.info("  ⚠️ 如果需要双向通信，需要使用共享变量（如 ConcurrentHashMap、AtomicReference 等）");
    }
    
    /**
     * 演示 TTL 支持异步嵌套场景: 父线程 -> 子线程 -> 孙线程
     */
    private static void testNestedAsync() throws Exception {
        TTL_CONTEXT.set("parent-context");
        log.info("[主线程] 设置上下文: {}", TTL_CONTEXT.get());
    
        // 第一层异步
        TTL_EXECUTOR_SERVICE.submit(() -> {
            log.info("[子线程-1] 读取上下文: {}", TTL_CONTEXT.get());
    
            // 在子线程中修改上下文
            TTL_CONTEXT.set("child-context");
            log.info("[子线程-1] 修改上下文为: {}", TTL_CONTEXT.get());
    
            // 第二层异步: 子线程提交任务到线程池
            try {
                TTL_EXECUTOR_SERVICE.submit(() -> {
                    log.info("[孙线程-2] 读取上下文: {}", TTL_CONTEXT.get());
    
                    // 再次修改
                    TTL_CONTEXT.set("grandchild-context");
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