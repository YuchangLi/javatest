package com.java11.design.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVProcessor extends AbstractFileProcessor<List<String[]>> {

    private static final Logger log = LoggerFactory.getLogger(CSVProcessor.class);

    @Override
    protected List<String[]> readData(String inputFile) throws IOException {
        log.info("CSV 读取器开始读取文件: {}", inputFile);
        var result = new ArrayList<String[]>();
        try (var reader = Files.newBufferedReader(Paths.get(inputFile), StandardCharsets.UTF_8)) {
            String line;
            var lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                var fields = line.split(",");
                result.add(fields);
                log.debug("第 {} 行: {} 个字段", lineNumber, fields.length);
            }
        }
        log.info("CSV 读取完成，共 {} 行", result.size());
        return result;
    }

    @Override
    protected void writeData(String outputFile, List<String[]> data) throws IOException {
        log.info("CSV 写入器开始写入文件: {}", outputFile);
        try (var writer = Files.newBufferedWriter(Paths.get(outputFile), StandardCharsets.UTF_8)) {
            for (var row : data) {
                var line = String.join(",", row);
                writer.write(line);
                writer.newLine();
            }
        }
        log.info("CSV 写入完成");
    }

    @Override
    protected void validateData(List<String[]> data) {
        log.info("CSV 数据验证开始");
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("CSV 数据不能为空");
        }
        var fieldCount = data.get(0).length;
        for (var row : data) {
            if (row.length != fieldCount) {
                log.warn("字段数量不一致: 预期 {}, 实际 {}", fieldCount, row.length);
            }
        }
        log.info("CSV 数据验证完成，共 {} 行，每行 {} 个字段", data.size(), fieldCount);
    }

    @Override
    protected void logResult(String inputFile, String outputFile) {
        log.info("CSV 处理结果: 从 {} 处理完成，输出到 {}", inputFile, outputFile);
    }
}