package com.java11.algorithm;

public class KmpNext {

    /**
     * 构建 next 数组（一维 DP）
     * next[j] 表示 pattern[0..j-1] 的最长相等真前后缀长度
     */
    public static int[] buildNext(String pattern) {
        int m = pattern.length();
        int[] next = new int[m];
        
        // 初始化：next[0] = 0 （Java 默认就是 0）
        // k 代表当前已匹配的前后缀长度（即上一个状态的 next 值）
        int k = 0; 

        // 从 j = 1 开始推导（DP 填表）
        for (int j = 1; j < m; j++) {
            // 【情况 B：断链回退】
            // 只要不匹配且 k > 0，就一直利用子问题结果回退
            while (k > 0 && pattern.charAt(j) != pattern.charAt(k)) {
                k = next[k]; 
            }

            // 【情况 A：完美衔接】
            if (pattern.charAt(j) == pattern.charAt(k)) {
                k++;
            }

            // 记录当前状态的 DP 结果
            next[j] = k;
        }
        return next;
    }

    // 测试
    public static void main(String[] args) {
        String pattern = "ABABC";
        int[] next = buildNext(pattern);
        
        System.out.print("next 数组: [");
        for (int i = 0; i < next.length; i++) {
            System.out.print(next[i]);
            if (i < next.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}