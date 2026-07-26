## 1. 依赖确认

- [x] 1.1 确认 Jackson ObjectMapper 可用（检查 pom.xml 依赖树）

## 2. JSONProcessor 修复

- [x] 2.1 修改 `readData()`：使用 ObjectMapper 解析 JSON 为 Map<String, Object>
- [x] 2.2 修改 `writeData()`：使用 ObjectMapper 将 Map 序列化为 JSON 写入文件
- [x] 2.3 更新 `validateData()`：移除对 content 字段的特殊检查，改为通用 Map 验证

## 3. 验证

- [x] 3.1 运行 `mvn compile` 确认编译通过
- [x] 3.2 运行 `TemplateMethodDemo` 确认 output_with_hook.json 包含 timestamp 字段
