package com.liyc.demo.log.woo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Woo {
    static final Logger logger = LoggerFactory.getLogger(Woo.class);

    public void doIt() {
        logger.debug("Woo debug!");
        logger.info("Woo info");
        logger.warn("Woo warn");
        logger.error("Woo error");
    }
}
