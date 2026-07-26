## Why

在学习设计模式的过程中，项目缺少模板方法模式的演示。模板方法模式是行为型设计模式中的重要模式，它定义算法骨架，将某些步骤延迟到子类实现，子类可以重定义算法的某些步骤而不改变算法结构。添加这个模式的demo有助于完整理解设计模式体系，特别是与项目中已有的策略模式形成对比学习。

## What Changes

- 新增模板方法模式的设计演示模块
- 创建抽象类 `AbstractFileProcessor`，定义文件处理的算法骨架
- 实现两个具体处理器：`CSVProcessor` 和 `JSONProcessor`
- 创建演示类 `TemplateMethodDemo`，展示不同处理器的使用
- 使用Java 11特性和项目现有的编码规范（Lombok、SLF4J日志）

## Capabilities

### New Capabilities
- `template-method-pattern`: 模板方法模式的完整演示，包括抽象类定义、具体子类实现和演示代码

### Modified Capabilities
- 无

## Impact

- 新增包结构：`com.java11.design.template`
- 新增类文件：4-5个Java类
- 不影响现有代码和功能
- 增加项目对设计模式的覆盖度，从7种增加到8种