package com.java11.design.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class AbstractFileProcessor<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractFileProcessor.class);

    public final void processFile(String inputFile, String outputFile) throws IOException {
        log.info("开始处理文件: {} -> {}", inputFile, outputFile);

        validateInput(inputFile);

        var data = readData(inputFile);
        log.info("读取数据完成");

        var transformedData = transformData(data);
        if (transformedData != data) {
            log.info("数据转换完成");
        }

        validateData(transformedData);

        writeData(outputFile, transformedData);

        logResult(inputFile, outputFile);
    }

    protected void validateInput(String inputFile) throws IOException {
        if (inputFile == null || inputFile.trim().isEmpty()) {
            throw new IllegalArgumentException("输入文件路径不能为空");
        }
        log.debug("输入文件验证通过: {}", inputFile);
    }

    protected abstract T readData(String inputFile) throws IOException;

    protected T transformData(T data) {
        log.debug("使用默认的数据转换（无转换）");
        return data;
    }

    protected void validateData(T data) {
        log.debug("使用默认的数据验证（无验证）");
    }

    protected abstract void writeData(String outputFile, T data) throws IOException;

    protected void logResult(String inputFile, String outputFile) {
        log.info("文件处理完成: {} -> {}", inputFile, outputFile);
    }
}