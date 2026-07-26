package com.java11.design.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TemplateMethodDemo {

    private static final Logger log = LoggerFactory.getLogger(TemplateMethodDemo.class);

    private static final String RESOURCES_DIR = "src/test/resources/template-data/";

    public static void main(String[] args) {
        log.info("=== 模板方法模式演示 ===");

        try {
            createCSVTestData();
            createJSONTestData();

            testCSVProcessor();
            testJSONProcessorWithHook();
            testJSONProcessorDefault();

        } catch (Exception e) {
            log.error("演示执行出错", e);
        }
    }

    private static void createCSVTestData() throws IOException {
        log.info("创建 CSV 测试数据...");
        var inputFile = RESOURCES_DIR + "input.csv";
        Files.createDirectories(Paths.get(inputFile).getParent());
        try (var writer = Files.newBufferedWriter(Paths.get(inputFile))) {
            writer.write("张三,30,工程师");
            writer.newLine();
            writer.write("李四,25,设计师");
            writer.newLine();
            writer.write("王五,35,产品经理");
        }
        log.info("CSV 测试数据创建完成: {}", inputFile);
    }

    private static void createJSONTestData() throws IOException {
        log.info("创建 JSON 测试数据...");
        var inputFile = RESOURCES_DIR + "input.json";
        try (var writer = Files.newBufferedWriter(Paths.get(inputFile))) {
            writer.write("{\"name\":\"张三\",\"age\":30,\"title\":\"工程师\"}");
        }
        log.info("JSON 测试数据创建完成: {}", inputFile);
    }

    private static void testCSVProcessor() throws IOException {
        log.info("\n=== 测试 CSV 处理器 ===");
        var csvProcessor = new CSVProcessor();
        var inputFile = RESOURCES_DIR + "input.csv";
        var outputFile = RESOURCES_DIR + "output.csv";
        csvProcessor.processFile(inputFile, outputFile);
    }

    private static void testJSONProcessorWithHook() throws IOException {
        log.info("\n=== 测试 JSON 处理器（重写钩子方法）===");
        var jsonProcessorWithHook = new JSONProcessor() {
            @Override
            protected java.util.Map<String, Object> transformData(java.util.Map<String, Object> data) {
                log.info("自定义数据转换: 添加时间戳");
                var transformed = new java.util.HashMap<>(data);
                transformed.put("timestamp", System.currentTimeMillis());
                return transformed;
            }
        };
        var inputFile = RESOURCES_DIR + "input.json";
        var outputFile = RESOURCES_DIR + "output_with_hook.json";
        jsonProcessorWithHook.processFile(inputFile, outputFile);
    }

    private static void testJSONProcessorDefault() throws IOException {
        log.info("\n=== 测试 JSON 处理器（默认钩子方法）===");
        var jsonProcessor = new JSONProcessor();
        var inputFile = RESOURCES_DIR + "input.json";
        var outputFile = RESOURCES_DIR + "output_default.json";
        jsonProcessor.processFile(inputFile, outputFile);
    }
}