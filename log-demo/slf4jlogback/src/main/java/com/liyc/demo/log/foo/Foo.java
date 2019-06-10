package com.liyc.demo.log.foo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Foo {
    static final Logger logger = LoggerFactory.getLogger(Foo.class);

    public void doIt() {
        logger.debug("Foo debug!");
        logger.info("Foo info");
    }
}
