## ADDED Requirements

### Requirement: JSON 读取解析为结构化 Map
`JSONProcessor.readData()` SHALL 将 JSON 文件解析为 `Map<String, Object>`，其中每个 JSON 字段对应 Map 的一个键值对。

#### Scenario: 读取简单 JSON 对象
- **WHEN** 输入文件包含 `{"name":"张三","age":30}`
- **THEN** 返回的 Map SHALL 包含键 `"name"` 值为 `"张三"`，键 `"age"` 值为 `30`

### Requirement: JSON 写入序列化结构化 Map
`JSONProcessor.writeData()` SHALL 将 `Map<String, Object>` 序列化为 JSON 字符串写入输出文件。

#### Scenario: 写入包含额外字段的 Map
- **WHEN** Map 包含 `"name":"张三"` 和 `"timestamp":1234567890`
- **THEN** 输出文件 SHALL 包含 `{"name":"张三","timestamp":1234567890}`

### Requirement: transformData 钩子修改生效
`transformData()` 钩子方法添加或修改的字段 SHALL 在输出文件中体现。

#### Scenario: 钩子添加 timestamp 字段
- **WHEN** `transformData()` 向 Map 添加 `"timestamp"` 字段
- **THEN** 输出 JSON 文件 SHALL 包含该 `timestamp` 字段
