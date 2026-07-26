## Why

`JSONProcessor` 的 `writeData()` 方法只写入 `data.get("content")`（原始 JSON 字符串），忽略了 `transformData()` 钩子方法添加到 map 中的其他字段（如 `timestamp`）。这导致模板方法模式的核心价值——数据转换——在 JSON 处理器中完全失效。

## What Changes

- 修复 `JSONProcessor.readData()`：解析 JSON 为真正的 Map 结构，而非存储原始字符串
- 修复 `JSONProcessor.writeData()`：从结构化 Map 重建 JSON 并写入
- 确保 `transformData()` 钩子的修改能正确反映到输出文件

## Capabilities

### New Capabilities
- `json-data-transform`: JSON 处理器的数据读取、转换、写入流程

### Modified Capabilities

## Impact

- 受影响文件：`JSONProcessor.java`
- 无 API 变更，无依赖变更
- 演示类 `TemplateMethodDemo` 无需修改
