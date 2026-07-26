## 1. 项目结构创建

- [x] 1.1 创建 `com.java11.design.template` 包结构
- [x] 1.2 创建测试数据文件目录

## 2. 抽象类实现

- [x] 2.1 创建 `AbstractFileProcessor<T>` 抽象类
- [x] 2.2 实现 `processFile()` 模板方法（final）
- [x] 2.3 定义 `validateInput()` 方法（可重写）
- [x] 2.4 定义 `readData()` 抽象方法
- [x] 2.5 定义 `transformData()` 钩子方法（默认空实现）
- [x] 2.6 定义 `validateData()` 方法（可重写）
- [x] 2.7 定义 `writeData()` 抽象方法
- [x] 2.8 定义 `logResult()` 方法（可重写）
- [x] 2.9 添加 SLF4J 日志记录

## 3. CSV 处理器实现

- [x] 3.1 创建 `CSVProcessor` 类继承 `AbstractFileProcessor<String[]>`
- [x] 3.2 实现 `readData()` 方法，解析 CSV 格式
- [x] 3.3 实现 `writeData()` 方法，生成 CSV 格式
- [x] 3.4 重写 `validateData()` 方法，验证 CSV 数据格式
- [x] 3.5 重写 `logResult()` 方法，记录处理结果

## 4. JSON 处理器实现

- [x] 4.1 创建 `JSONProcessor` 类继承 `AbstractFileProcessor<Map<String, Object>>`
- [x] 4.2 实现 `readData()` 方法，解析 JSON 格式
- [x] 4.3 实现 `writeData()` 方法，生成 JSON 格式
- [x] 4.4 重写 `validateData()` 方法，验证 JSON 数据格式
- [x] 4.5 重写 `logResult()` 方法，记录处理结果

## 5. 演示类和测试数据

- [x] 5.1 创建 `TemplateMethodDemo` 类
- [x] 5.2 在演示类中创建 CSV 测试数据文件
- [x] 5.3 在演示类中创建 JSON 测试数据文件
- [x] 5.4 实现演示 CSV 处理器的 main 方法逻辑
- [x] 5.5 实现演示 JSON 处理器的 main 方法逻辑
- [x] 5.6 展示钩子方法的使用（默认实现 vs 自定义实现）
- [x] 5.7 添加日志输出展示处理流程

## 6. 代码质量验证

- [x] 6.1 确保所有类都使用 SLF4J 日志
- [x] 6.2 确保使用 Java 11 特性（var、try-with-resources）
- [x] 6.3 确保代码风格与现有设计模式一致
- [x] 6.4 运行 `mvn compile` 验证编译通过
- [x] 6.5 运行 `TemplateMethodDemo` 验证功能正确