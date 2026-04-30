# Workspace Guide

This is a **personal Java learning/demo workspace** — a collection of independent projects, NOT a Maven multi-module build. There is no root `pom.xml`.

## Working with this repo

- **Each subdirectory is its own standalone project** with its own `pom.xml` (or build system).
- Always `cd` into the target subdirectory before running Maven commands.
- No root-level build/test/lint command exists.

## Subprojects

| Directory | Description | Java | Notes |
|-----------|-------------|------|-------|
| `java11/` | Java 11 feature demos (concurrency, RxJava 2, CompletableFuture, ThreadLocal, Lombok) | 11 | Has its own AGENTS.md |
| `java8/` | Java 8 feature demos | 8 | |
| `boot-jsp/` | Spring Boot 2.1.5 + JSP demo | 8 | Uses `spring-boot-maven-plugin` |
| `dubbo/` | Dubbo RPC demos (`restdemo/`, `springboot_dubbo_parent/`) | | |
| `redisson/` | Redisson distributed lock demos (`redissondemo/`, `redissontest/`) | | |
| `spi/` | Java SPI demos (`spi-impl/`, `spi-service/`) | | |
| `loctest-maven-plugin/` | Custom Maven plugin (maven-plugin packaging) | 8 | Uses maven-invoker-plugin for ITs |
| `jvm-demo/` | JVM/GC tuning demos | | |
| `log-demo/` | Logging demos (logback-boot, slf4jlogback) | | |
| `thread-local-demo/` | ThreadLocal demos | | |
| `greenwich_web/` | Spring Cloud Greenwich web demo | | |
| `design-patterns/` | Design pattern demos (e.g. `decorator-model/`) | | |
| `moduletest/` | Java Platform Module System (JPMS) demo | | Uses `build/` output |

## Build commands

```bash
cd <subdirectory>
mvn compile          # compile
mvn package          # package (produces jar/war in target/)
mvn clean            # clean build artifacts
mvn spring-boot:run  # run Spring Boot apps (boot-jsp/, etc.)
```

## Conventions

- Group ID pattern: `com.liyuchang.test` or `com.liyc.*`
- All projects use Maven unless otherwise noted
- No test framework configured at workspace level — individual projects may have JUnit
- `.gitignore` ignores `target/`, `bin/`, `.idea/`, IDE files, and compiled artifacts
