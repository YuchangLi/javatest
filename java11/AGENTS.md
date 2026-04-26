# Java 11 测试项目 - 架构概览

## 项目概述

这是一个 Java 11 测试/演示项目，用于展示各种 Java 特性和编程概念。项目按功能组织成多个包，每个包专注于特定的 Java 能力。

**主要功能：**
- Java 并发工具类（线程、执行器、锁、原子类）
- 异步编程的 Future 和 CompletableFuture
- RxJava 2 响应式编程
- ThreadLocal 和 TransmittableThreadLocal 示例
- 设计模式（单例模式）
- Java 8/10/11 语言特性
- 垃圾回收测试
- 各种 Java 核心库演示

## 构建与命令

项目使用 **Maven** 作为构建工具。

**常用命令：**
- 编译：`mvn compile`
- 清理：`mvn clean`
- 打包：`mvn package`
- 运行特定测试类：直接从 IDE 或通过 `java` 命令执行带 `main` 方法的单个 Java 类

## 代码风格

- 使用 Java 11 语言特性
- 使用 Lombok 减少样板代码
- 日志：SLF4J + Logback 实现
- 按功能/概念组织包结构

## 测试

这主要是一个演示项目，包含可执行的测试类（每个都有 `main` 方法）。未配置专用测试框架，但类文件演示了各种 Java 行为和概念。

## 安全

- 无特定安全配置 - 这是一个测试/演示项目
- 无对外 API 或服务
- 无敏感数据处理

## 配置

**Java 版本：** Java 11

**主要依赖：**
- RxJava 2: 2.2.8
- Lombok: 1.18.36
- TransmittableThreadLocal: 2.14.4
- SLF4J API: 1.7.36
- Logback Classic: 1.2.12
- Hutool: 5.8.21

**构建工具：** Maven 3.x 与 maven-compiler-plugin 3.8.1

**编码：** UTF-8

## Trae 规则

来自 `.trae/rules/structural.md`：
- 项目使用 Java 11
