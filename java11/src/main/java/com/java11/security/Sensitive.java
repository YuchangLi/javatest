package com.java11.security;

import cn.hutool.dfa.WordTree;

import java.util.List;

public class Sensitive {
    public static void main(String[] args) {
        WordTree wordTree = new WordTree();
        wordTree.addWord("大");
        wordTree.addWord("大憨憨");
        wordTree.addWord("憨憨");

        String text = "那人真是个大憨憨！";

        // 获得第一个匹配的关键字
        String matchStr = wordTree.match(text);
        System.out.println(matchStr); // 输出: 大

        // matchAll(text, limit, isDensityMatch, isGreedy)
        // - limit: 匹配数量上限，-1 表示不限制
        // - isDensityMatch: 是否密度匹配（在已匹配词内部继续寻找重叠词）
        // - isGreedy: 是否贪婪匹配（true 匹配最长关键词，false 匹配最短关键词）
        List<String> matchStrList = wordTree.matchAll(text, -1, false, false);
        System.out.println(matchStrList); // 输出: [大, 憨憨]

        List<String> matchStrList2 = wordTree.matchAll(text, -1, false, true);
        System.out.println(matchStrList2); // &#x8F93;&#x51FA;: [&#x5927;, &#x5927;&#x61A8;&#x61A8;]
    }
}
