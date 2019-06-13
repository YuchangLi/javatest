package com.liyc.demo.log;

import com.liyc.demo.log.foo.Foo;
import com.liyc.demo.log.woo.Woo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class App {
    static  Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        // print logback's internal status
//        StatusPrinter.print(lc);
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        try {
            int i = 1/0;
        } catch (Exception e) {
            logger.error("error = {},{},{},{}", 1, 2, 3, 4, e);
        }
        Foo foo  = new Foo();
        foo.doIt();
        Woo woo  = new Woo();
        woo.doIt();
    }
}
