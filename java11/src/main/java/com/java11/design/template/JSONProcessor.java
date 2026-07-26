package com.java11.design.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.json.JSONUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JSONProcessor extends AbstractFileProcessor<Map<String, Object>> {

    private static final Logger log = LoggerFactory.getLogger(JSONProcessor.class);

    @Override
    protected Map<String, Object> readData(String inputFile) throws IOException {
        log.info("JSON 读取器开始读取文件: {}", inputFile);
        Map<String, Object> result;
        try (var reader = Files.newBufferedReader(Paths.get(inputFile), StandardCharsets.UTF_8)) {
            var content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            log.info("JSON 内容: {}", content.toString());
            result = JSONUtil.parseObj(content.toString()).toBean(Map.class);
        }
        log.info("JSON 读取完成");
        return result;
    }

    @Override
    protected void writeData(String outputFile, Map<String, Object> data) throws IOException {
        log.info("JSON 写入器开始写入文件: {}", outputFile);
        try (var writer = Files.newBufferedWriter(Paths.get(outputFile), StandardCharsets.UTF_8)) {
            writer.write(JSONUtil.toJsonPrettyStr(data));
        }
        log.info("JSON 写入完成");
    }

    @Override
    protected void validateData(Map<String, Object> data) {
        log.info("JSON 数据验证开始");
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("JSON 数据不能为空");
        }
        log.info("JSON 数据验证完成，字段数: {}", data.size());
    }

    @Override
    protected void logResult(String inputFile, String outputFile) {
        log.info("JSON 处理结果: 从 {} 处理完成，输出到 {}", inputFile, outputFile);
    }
}