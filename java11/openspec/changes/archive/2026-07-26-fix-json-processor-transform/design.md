## Context

`JSONProcessor` 继承 `AbstractFileProcessor<Map<String, Object>>`，模板方法 `processFile()` 依次调用 `readData()` → `transformData()` → `validateData()` → `writeData()`。

当前 `readData()` 将原始 JSON 字符串存为 `data["content"]`，`writeData()` 只读 `data["content"]` 写回。`transformData()` 钩子虽然能修改 map，但其结果在 `writeData()` 中被完全忽略。

## Goals / Non-Goals

**Goals:**
- 修复 JSON 数据在 read/transform/write 全链路中的结构化流转
- 保持模板方法模式的设计意图：数据在读取时结构化，转换和写入都基于结构化数据

**Non-Goals:**
- 不修改 `AbstractFileProcessor` 基类
- 不修改 `CSVProcessor`
- 不修改 `TemplateMethodDemo`

## Decisions

**决策 1：使用 Jackson 解析 JSON**

项目已有 Jackson 依赖（通过 RxJava 间接引入）。直接使用 `ObjectMapper` 将 JSON 字符串解析为 `Map<String, Object>`，写入时再序列化回 JSON。

替代方案：
- 手动解析 JSON（复杂且易错）
- 使用 Hutool 的 `JSONUtil`（项目已有 Hutool 依赖）

选择 Jackson 因为它是 Java JSON 处理的事实标准，且已在依赖树中。

**决策 2：`readData()` 返回完整的结构化 Map**

`readData()` 解析 JSON 为 `Map<String, Object>`，而非存储原始字符串。这样 `transformData()` 和 `writeData()` 都基于同一结构化数据操作。

## Risks / Trade-offs

- [风险] Jackson 可能不在直接依赖中 → 通过 Maven 依赖树确认，或改用 Hutool
- [风险] 嵌套 JSON 对象/数组的序列化 → 使用 `ObjectMapper` 的默认行为即可
