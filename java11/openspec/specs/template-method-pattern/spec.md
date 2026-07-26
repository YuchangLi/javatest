## Purpose

模板方法模式（Template Method Pattern）用于定义文件处理的算法骨架，将不变的步骤封装在基类中，可变的步骤延迟到子类实现。

## Requirements

### Requirement: 模板方法模式抽象类
系统 SHALL 提供抽象类 `AbstractFileProcessor`，定义文件处理的算法骨架，包含模板方法、抽象方法和钩子方法。

#### Scenario: 抽象类定义完整性
- **WHEN** 查看抽象类 `AbstractFileProcessor`
- **THEN** 系统 SHALL 包含模板方法 `processFile()` 并标记为 final
- **THEN** 系统 SHALL 包含抽象方法 `readData()` 和 `writeData()`
- **THEN** 系统 SHALL 包含钩子方法 `transformData()` 并提供默认空实现
- **THEN** 系统 SHALL 包含可重写方法 `validateInput()`、`validateData()` 和 `logResult()`

#### Scenario: 模板方法执行流程
- **WHEN** 调用 `processFile(String inputFile, String outputFile)`
- **THEN** 系统 SHALL 按顺序执行：validateInput() → readData() → transformData() → validateData() → writeData() → logResult()
- **THEN** 系统 SHALL 在 readData() 和 writeData() 失败时抛出 IOException

### Requirement: CSV 文件处理器
系统 SHALL 提供具体类 `CSVProcessor` 继承 `AbstractFileProcessor`，实现 CSV 文件的读取和写入。

#### Scenario: CSV 文件读取
- **WHEN** `CSVProcessor` 处理 CSV 格式文件
- **THEN** `readData()` 方法 SHALL 解析 CSV 格式数据
- **THEN** 系统 SHALL 将每一行解析为 String 数组
- **THEN** 系统 SHALL 返回 `List<String[]>` 类型的数据

#### Scenario: CSV 文件写入
- **WHEN** `CSVProcessor` 写入 CSV 格式文件
- **THEN** `writeData()` 方法 SHALL 将数据序列化为 CSV 格式
- **THEN** 系统 SHALL 使用逗号分隔字段
- **THEN** 系统 SHALL 每行数据写入一行

### Requirement: JSON 文件处理器
系统 SHALL 提供具体类 `JSONProcessor` 继承 `AbstractFileProcessor`，实现 JSON 文件的读取和写入。

#### Scenario: JSON 文件读取
- **WHEN** `JSONProcessor` 处理 JSON 格式文件
- **THEN** `readData()` 方法 SHALL 解析 JSON 格式数据
- **THEN** 系统 SHALL 返回 `Map<String, Object>` 类型的数据
- **THEN** 系统 SHALL 支持嵌套的 JSON 对象和数组

#### Scenario: JSON 文件写入
- **WHEN** `JSONProcessor` 写入 JSON 格式文件
- **THEN** `writeData()` 方法 SHALL 将数据序列化为 JSON 格式
- **THEN** 系统 SHALL 使用缩进格式化输出
- **THEN** 系统 SHALL 正确处理 Map 和 List 类型

### Requirement: 演示类和测试数据
系统 SHALL 提供演示类 `TemplateMethodDemo` 展示模板方法模式的使用，并创建测试数据文件。

#### Scenario: 演示类可执行性
- **WHEN** 运行 `TemplateMethodDemo` 的 main 方法
- **THEN** 系统 SHALL 创建 CSV 测试数据文件
- **THEN** 系统 SHALL 创建 JSON 测试数据文件
- **THEN** 系统 SHALL 使用 CSVProcessor 处理 CSV 文件并输出日志
- **THEN** 系统 SHALL 使用 JSONProcessor 处理 JSON 文件并输出日志

#### Scenario: 钩子方法演示
- **WHEN** `TemplateMethodDemo` 执行文件处理
- **THEN** 系统 SHALL 展示钩子方法的可选性
- **THEN** 系统 SHALL 在日志中标记哪些步骤是默认实现
- **THEN** 系统 SHALL 在日志中标记哪些步骤是自定义实现

### Requirement: 代码规范和项目集成
系统 SHALL 遵循项目现有的代码规范和架构，确保新代码与现有代码风格一致。

#### Scenario: 包结构一致性
- **WHEN** 创建模板方法模式相关类
- **THEN** 系统 SHALL 将类放置在 `com.java11.design.template` 包下
- **THEN** 系统 SHALL 遵循现有设计模式的包结构规范

#### Scenario: Java 11 特性使用
- **WHEN** 编写代码
- **THEN** 系统 SHALL 使用 var 关键字进行类型推断（在适用场景）
- **THEN** 系统 SHALL 使用 try-with-resources 自动关闭文件流
- **THEN** 系统 SHALL 使用 Java 11 标准库处理文件 I/O

#### Scenario: 日志集成
- **WHEN** 类需要记录日志
- **THEN** 系统 SHALL 使用 SLF4J 和 Logback
- **THEN** 系统 SHALL 在关键步骤记录日志（文件处理开始、结束、错误）
