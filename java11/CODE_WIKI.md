# Java 11 测试项目 - Code Wiki

## 1. 项目概述

这是一个 **Java 11 特性测试与演示项目**，用于展示 Java 并发编程、响应式编程、JDK 新特性等核心技术。项目以可执行的测试类形式呈现，每个类都包含 `main` 方法，便于直接运行和观察效果。

### 1.1 项目定位

- **教育性质**: 帮助开发者理解 Java 并发模型、线程安全、异步编程等核心概念
- **演示性质**: 提供可运行的代码示例，直观展示各种 Java 特性的行为
- **参考性质**: 作为学习和查阅 Java 并发编程的参考资料

### 1.2 技术栈

| 分类 | 技术 | 版本 |
| :--- | :--- | :--- |
| 语言 | Java | 11 |
| 构建工具 | Maven | 3.x |
| 响应式框架 | RxJava | 2.2.8 |
| ThreadLocal | TransmittableThreadLocal | 2.14.4 |
| 日志 | SLF4J + Logback | 1.7.36 / 1.2.12 |
| 工具库 | Hutool | 5.8.21 |
| 代码简化 | Lombok | 1.18.36 |

---

## 2. 整体架构

### 2.1 模块结构

```
src/main/java/com/java11/
├── aqs/              # AQS 抽象队列同步器演示
├── atomic/           # 原子类操作演示
├── delayQueue/       # 延迟队列演示
├── design/           # 设计模式
│   └── single/       # 单例模式实现
├── equals/           # 相等性判断测试
├── future/           # Future 与 CompletableFuture
├── gc/               # 垃圾回收测试
├── http/             # Java 11 HTTP 客户端
├── integer/          # Integer 相关测试
├── localDate/        # Java 8 LocalDate 测试
├── parallel/         # 并行编程
├── productConsumeModel/ # 生产者-消费者模型
├── rxjava/           # RxJava 2 响应式编程
├── string/           # String 相关测试
├── thread/           # 线程基础操作
│   └── excutor/      # 执行器框架
├── tree/             # 树形结构
├── unsafe/           # Unsafe API 演示
├── var/              # Java 10 var 特性
└── volatilee/        # volatile 关键字测试
```

### 2.2 模块依赖关系

```
                    ┌─────────────────────────────────┐
                    │         核心模块                 │
                    └─────────────────────────────────┘
                                   │
        ┌───────────┬──────────────┼──────────────┬───────────┐
        ▼           ▼              ▼              ▼           ▼
    ┌────────┐ ┌─────────┐   ┌──────────┐   ┌────────┐ ┌──────────┐
    │ thread │ │  atomic │   │   aqs    │   │future  │ │ volatilee│
    └────┬───┘ └────┬────┘   └────┬─────┘   └────┬───┘ └────┬─────┘
         │          │             │               │           │
         ▼          ▼             ▼               ▼           ▼
    ┌──────────────────────────────────────────────────────────────┐
    │              业务模块（productConsumeModel, parallel）        │
    └──────────────────────────────────────────────────────────────┘
                                   │
                                   ▼
                    ┌─────────────────────────────────┐
                    │        扩展模块                  │
                    │ (rxjava, unsafe, gc, http)      │
                    └─────────────────────────────────┘
```

---

## 3. 模块职责说明

### 3.1 AQS 模块 (`aqs/`)

**职责**: 演示 AbstractQueuedSynchronizer 的独占模式与共享模式

| 文件 | 说明 |
| :--- | :--- |
| `ExclusiveVsSharedDemo.java` | 对比 ReentrantLock（独占）与 Semaphore（共享）|
| `SimpleBlockingQueue.java` | 基于 AQS 实现的简单阻塞队列 |
| `SimpleCountDownLatch.java` | 基于 AQS 实现的 CountDownLatch |
| `SimpleSemaphore.java` | 基于 AQS 实现的信号量 |

### 3.2 原子类模块 (`atomic/`)

**职责**: 演示 Java 原子操作类的使用

| 文件 | 说明 |
| :--- | :--- |
| `AtomicIntegerTest.java` | AtomicInteger 的 CAS 操作演示 |
| `AtomicLongTest.java` | AtomicLong 的使用示例 |
| `AtomicReferenceTest.java` | 原子引用类型操作 |
| `AtomicStampedReferenceTest.java` | 带版本戳的原子引用（解决 ABA 问题）|

### 3.3 延迟队列模块 (`delayQueue/`)

**职责**: 演示 DelayQueue 的使用场景

| 文件 | 说明 |
| :--- | :--- |
| `DelayQueueTest.java` | 基本延迟队列使用 |
| `DelayQueueMulConsumerTest.java` | 多消费者场景 |

### 3.4 设计模式模块 (`design/single/`)

**职责**: 演示单例模式的不同实现

| 文件 | 说明 |
| :--- | :--- |
| `SingleStaticTest.java` | 静态内部类单例 |
| `SingleVolatileTest.java` | 双重检查锁单例（volatile）|

### 3.5 Future 模块 (`future/`)

**职责**: 演示异步编程中的 Future 模式

| 文件 | 说明 |
| :--- | :--- |
| `FutureTest.java` | 传统 Future 接口使用 |
| `CompletableFutureTest.java` | CompletableFuture 组合操作 |

### 3.6 并行编程模块 (`parallel/`)

**职责**: 演示 Java 并行计算能力

| 文件 | 说明 |
| :--- | :--- |
| `ExecutorServiceTest.java` | 线程池与 CompletionService |
| `ForkJoinTest.java` | Fork/Join 框架使用 |
| `SingleThread.java` | 单线程执行示例 |
| `StreamTest.java` | 并行流处理 |
| `ThreadTest.java` | 基础线程测试 |

### 3.7 生产者-消费者模块 (`productConsumeModel/`)

**职责**: 演示经典的生产者-消费者问题解决方案

| 文件 | 说明 |
| :--- | :--- |
| `BlockingDequeTest.java` | 双端阻塞队列 |
| `BlockingQueueTest.java` | 阻塞队列实现 |
| `CountDownLatchTest.java` | 倒计时门闩 |
| `CyclicBarrierTest.java` | 循环屏障 |
| `ReentrantLockModel.java` | 可重入锁 |
| `SemaphoreTest.java` | 信号量 |
| `WaitNotify.java` | Object.wait/notify 机制 |

### 3.8 RxJava 模块 (`rxjava/`)

**职责**: 演示响应式编程范式

| 文件 | 说明 |
| :--- | :--- |
| `Rxjava2Java11Test.java` | RxJava 基本操作 |
| `Rxjava2OperatorTest.java` | RxJava 操作符演示 |
| `Rxjava2SourceTest.java` | RxJava 数据源创建 |

### 3.9 线程模块 (`thread/`)

**职责**: 演示线程基础操作与高级特性

| 文件 | 说明 |
| :--- | :--- |
| `DeadlockTest.java` | 死锁演示 |
| `LazySingletonTest.java` | 懒汉式单例 |
| `ReentrantLockTest.java` | 可重入锁测试 |
| `ThreadException.java` | 线程异常处理 |
| `ThreadLocalTest.java` | ThreadLocal 使用 |
| `ThreadTest.java` | 线程基础测试 |
| `Volatile.java` | volatile 关键字 |
| `TtlContextHolder.java` | TransmittableThreadLocal 演示 |
| `TtlContextHolder2.java` | TTL 进阶用法 |

#### 子模块 `excutor/`

| 文件 | 说明 |
| :--- | :--- |
| `CallableAndFuture.java` | Callable 与 Future 配合使用 |
| `CompletionServiceTest.java` | 异步任务完成服务 |
| `CountTask.java` | 计数任务示例 |
| `ExecutorUtil.java` | 执行器工具类 |
| `ExecutorsTest.java` | Executors 工厂方法 |
| `SingleThreadExecutorTest.java` | 单线程执行器 |

### 3.10 Volatile 模块 (`volatilee/`)

**职责**: 深入演示 volatile 关键字特性

| 文件 | 说明 |
| :--- | :--- |
| `VolatileArrays.java` | volatile 数组可见性 |
| `VolatileAtomicityTest.java` | volatile 不保证原子性 |
| `VolatileSingleton.java` | volatile 在单例中的应用 |
| `VolatileVisibilityTest.java` | volatile 可见性保证 |

### 3.11 其他模块

| 模块 | 职责 | 关键文件 |
| :--- | :--- | :--- |
| `equals/` | 相等性判断 | `EqualsTest.java` |
| `gc/` | 垃圾回收测试 | `FinalizeTest.java`, `PrinrtGCTest.java` |
| `http/` | Java 11 HTTP 客户端 | `Java11Test.java` |
| `integer/` | Integer 特性 | `IntegerTest.java` |
| `localDate/` | Java 8 日期时间 | `Java8Test.java` |
| `string/` | String 常量池 | `ConstantCacheTest.java` |
| `tree/` | 树形结构 | `TreeNode.java` |
| `unsafe/` | Unsafe API | `UnsafeTest.java` |
| `var/` | Java 10 var | `Java10Test.java` |

---

## 4. 关键类与函数说明

### 4.1 AQS 模块

#### `ExclusiveVsSharedDemo`

**功能**: 对比独占锁与共享锁的行为差异

| 方法 | 说明 |
| :--- | :--- |
| `main()` | 分别演示 ReentrantLock 和 Semaphore 的使用 |

**核心代码逻辑**:
- 独占模式：同一时刻只有 1 个线程能获取锁
- 共享模式：同一时刻最多 N 个线程能获取许可证

### 4.2 Future 模块

#### `CompletableFutureTest`

**功能**: 演示 CompletableFuture 的组合操作

| 方法 | 说明 |
| :--- | :--- |
| `allOfTest()` | 使用 `allOf()` 等待多个任务完成后执行后续操作 |

**核心特性**:
- `supplyAsync()`: 异步执行并返回结果
- `runAsync()`: 异步执行无返回值
- `allOf()`: 等待所有任务完成
- `thenRun()`: 任务完成后的回调

### 4.3 ThreadLocal 模块

#### `TtlContextHolder`

**功能**: 演示 TransmittableThreadLocal 在线程池中的上下文传递

| 方法 | 说明 |
| :--- | :--- |
| `testThreeThreadLocalsInThreadPool()` | 对比 ThreadLocal、InheritableThreadLocal、TTL |
| `testCaptureAtSubmitTime()` | TTL 在任务提交时捕获上下文 |
| `testModifyContextWithoutResubmit()` | 子线程无法感知父线程后续修改 |
| `testNestedAsync()` | 异步嵌套场景的上下文传递 |

**核心结论**:

| ThreadLocal 类型 | 线程池传递 | 手动线程传递 | 子线程感知父线程修改 |
| :--- | :--- | :--- | :--- |
| ThreadLocal | ❌ | ❌ | ❌ |
| InheritableThreadLocal | ❌（仅首次） | ✅ | ❌ |
| TransmittableThreadLocal | ✅ | ❌ | ❌ |

### 4.4 原子类模块

#### `AtomicIntegerTest`

**功能**: 演示 CAS（Compare-And-Swap）操作

| 方法 | 说明 |
| :--- | :--- |
| `main()` | 两个线程交替进行 CAS 操作 |

**核心操作**:
```java
atomicInt.compareAndSet(expectedValue, newValue);
```

### 4.5 Volatile 模块

#### `VolatileVisibilityTest`

**功能**: 演示 volatile 变量的可见性保证

| 方法 | 说明 |
| :--- | :--- |
| `main()` | 对比普通变量与 volatile 变量的可见性 |

**核心结论**:
- 普通变量：子线程可能看不到主线程的修改
- volatile 变量：子线程能立即看到主线程的修改

---

## 5. 依赖关系

### 5.1 依赖列表

| 依赖 | GroupId | ArtifactId | 版本 | 用途 |
| :--- | :--- | :--- | :--- | :--- |
| RxJava | `io.reactivex.rxjava2` | `rxjava` | 2.2.8 | 响应式编程 |
| Lombok | `org.projectlombok` | `lombok` | 1.18.36 | 简化代码 |
| TransmittableThreadLocal | `com.alibaba` | `transmittable-thread-local` | 2.14.4 | 线程池上下文传递 |
| SLF4J | `org.slf4j` | `slf4j-api` | 1.7.36 | 日志抽象 |
| Logback | `ch.qos.logback` | `logback-classic` | 1.2.12 | 日志实现 |
| Hutool | `cn.hutool` | `hutool-all` | 5.8.21 | 工具库 |

### 5.2 依赖关系图

```
                     project
                        │
        ┌───────────────┼───────────────┬───────────────────┐
        ▼               ▼               ▼                   ▼
    RxJava           Lombok        TTL ThreadLocal      SLF4J
                                      │                     │
                                      ▼                     ▼
                                Thread Pool            Logback
                                                              │
                                                              ▼
                                                          日志输出
```

---

## 6. 项目运行方式

### 6.1 环境要求

- Java 11 或更高版本
- Maven 3.x

### 6.2 构建命令

```bash
# 编译项目
mvn compile

# 清理项目
mvn clean

# 打包项目
mvn package
```

### 6.3 运行方式

由于这是一个演示项目，每个类都包含 `main` 方法，可以直接运行：

**方式一：IDE 运行**
1. 打开项目到 IntelliJ IDEA 或 Eclipse
2. 找到目标类（如 `com.java11.future.CompletableFutureTest`）
3. 右键选择 "Run"

**方式二：命令行运行**
```bash
# 编译后运行
cd target/classes
java com.java11.future.CompletableFutureTest
```

### 6.4 推荐学习路径

```
1. 基础概念
   └── thread/ThreadTest.java
   └── volatilee/VolatileVisibilityTest.java

2. 原子操作
   └── atomic/AtomicIntegerTest.java

3. 线程池
   └── parallel/ExecutorServiceTest.java
   └── thread/excutor/ExecutorsTest.java

4. Future 异步
   └── future/CompletableFutureTest.java

5. 生产者-消费者
   └── productConsumeModel/BlockingQueueTest.java

6. AQS 深入
   └── aqs/ExclusiveVsSharedDemo.java

7. ThreadLocal 进阶
   └── thread/TtlContextHolder.java

8. 响应式编程
   └── rxjava/Rxjava2Java11Test.java
```

---

## 7. 核心概念总结

### 7.1 并发编程要点

| 概念 | 说明 | 关键类 |
| :--- | :--- | :--- |
| 线程安全 | 多线程环境下数据一致性 | `synchronized`, `Lock` |
| CAS | 无锁原子操作 | `Atomic*` |
| 可见性 | 线程间变量可见 | `volatile` |
| 有序性 | 指令执行顺序 | `volatile`, `synchronized` |
| 线程池 | 线程复用 | `ExecutorService` |

### 7.2 设计模式应用

| 模式 | 实现位置 | 说明 |
| :--- | :--- | :--- |
| 单例模式 | `design/single/` | 静态内部类、双重检查锁 |
| 生产者-消费者 | `productConsumeModel/` | 阻塞队列实现 |
| 观察者模式 | `rxjava/` | 响应式数据流 |

---

## 附录：重要代码位置

| 功能 | 文件路径 |
| :--- | :--- |
| TTL 线程池上下文传递 | [TtlContextHolder.java](file:///d:/git/github/YuchangLi/javatest/java11/src/main/java/com/java11/thread/TtlContextHolder.java) |
| CompletableFuture 组合 | [CompletableFutureTest.java](file:///d:/git/github/YuchangLi/javatest/java11/src/main/java/com/java11/future/CompletableFutureTest.java) |
| Volatile 可见性 | [VolatileVisibilityTest.java](file:///d:/git/github/YuchangLi/javatest/java11/src/main/java/com/java11/volatilee/VolatileVisibilityTest.java) |
| AQS 独占vs共享 | [ExclusiveVsSharedDemo.java](file:///d:/git/github/YuchangLi/javatest/java11/src/main/java/com/java11/aqs/ExclusiveVsSharedDemo.java) |
| 原子类 CAS | [AtomicIntegerTest.java](file:///d:/git/github/YuchangLi/javatest/java11/src/main/java/com/java11/atomic/AtomicIntegerTest.java) |
