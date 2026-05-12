# CLAUDE.md

本文件为 Claude Code (claude.ai/code) 在此仓库中工作时提供指导。

## 项目概述

Java 11 测试/演示项目，用于展示 Java 并发、语言特性和各种库。每个类文件都是独立的，包含 `main` 方法用于演示运行。

## 构建命令

```bash
mvn compile          # 编译项目
mvn clean            # 清理构建产物
mvn package          # 打包为 JAR
```

直接从 IDE 运行单个演示，或通过命令行：
```bash
java -cp target/classes com.java11.aqs.SimpleCountDownLatch
```

## 架构

**按主题组织的包结构：**
- `aqs/` - AbstractQueuedSynchronizer 实现（SimpleCountDownLatch、SimpleSemaphore、SimpleBlockingQueue）
- `thread/` - 线程基础、ThreadLocal、执行器、死锁示例
- `productConsumeModel/` - 同步器（CountDownLatch、CyclicBarrier、Semaphore、ReentrantLock）
- `atomic/` - 原子类（AtomicInteger、AtomicReference、AtomicStampedReference）
- `volatilee/` - Volatile 关键字演示（可见性、原子性、单例模式）
- `future/` - Future 和 CompletableFuture 异步编程
- `rxjava/` - RxJava 2 响应式编程示例
- `gc/` - 垃圾回收和引用类型
- `design/single/` - 单例模式实现

**核心模式：**
- AQS 实现继承 `AbstractQueuedSynchronizer`，共享模式重写 `tryAcquireShared`/`tryReleaseShared`，独占模式重写 `tryAcquire`/`tryRelease`
- 生产者-消费者示例使用 `BlockingQueue`、`wait/notify` 或 `ReentrantLock` + `Condition`

## 依赖

- **RxJava 2** (2.2.8) - 响应式编程
- **Lombok** - 减少样板代码
- **TransmittableThreadLocal**（阿里）- 线程池中线程本地值传递
- **Hutool** - Java 工具库
- **SLF4J + Logback** - 日志

## 代码风格

- Java 11，使用 `var` 关键字
- 注释使用中文
- 每个演示类都有 `main` 方法，可独立运行
