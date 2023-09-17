package com.jvm.demo;

/**
 * @author liyuchang
 * @date 2023/07/27
 */
@AnnoTest(message = Constant.TYPE_CLASS)
public class DdocDemo {

    @AnnoTest(message = "method", apiType = "2")
    public String test(Integer t1, String t2) {
        return null;
    }

}
