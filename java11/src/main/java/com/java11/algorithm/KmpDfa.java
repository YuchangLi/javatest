package com.java11.algorithm;

import java.util.ArrayList;
import java.util.List;

public class KmpDfa {

    /**
     * Sedgewick restart 优化版 —— O(m × |Σ|) 时间, O(m × |Σ|) 空间
     *
     * @param pattern 模式串（仅支持 ASCII / Latin-1，即 char < 256）
     * @return dfa[state][char] = nextState，state 范围 [0..m]，共 m+1 个状态
     */
    public static int[][] buildDfa(String pattern) {
        int m = pattern.length();
        int R = 256; // 字母表大小
        int[][] dfa = new int[m + 1][R];

        if (m == 0) return dfa;

        // 基础情况：q0 读到 pattern[0] → q1
        dfa[0][pattern.charAt(0)] = 1;

        // restart 始终维护：当前状态 j 对应的"最长相等真前后缀"所指向的状态
        int restart = 0;

        for (int j = 1; j <= m; j++) {
            // ① 复制 restart 行的所有转移（失配时的默认行为）
            for (int c = 0; c < R; c++) {
                dfa[j][c] = dfa[restart][c];
            }

            // ② 覆盖正常匹配的转移（仅当 j < m 时才有下一个字符可匹配）
            if (j < m) {
                dfa[j][pattern.charAt(j)] = j + 1;
            }

            // ③ 更新 restart：用当前字符 pattern[j] 在 restart 行上走一步
            //    这等价于动态维护 next 数组
            if (j < m) {
                restart = dfa[restart][pattern.charAt(j)];
            }
        }

        return dfa;
    }

    /**
     * 暴力构建版 —— O(m² × |Σ|)，用于验证优化版的正确性
     * 严格按照"拼接串的最长后缀=模式前缀"规则计算每个转移
     */
    public static int[][] buildDfaBruteForce(String pattern) {
        int m = pattern.length();
        int R = 256;
        int[][] dfa = new int[m + 1][R];

        for (int j = 0; j <= m; j++) {
            for (int c = 0; c < R; c++) {
                // 拼接串 S = pattern[0..j-1] + (char)c
                String prefix = pattern.substring(0, j);
                String s = prefix + (char) c;

                int maxLen = 0;

                // 【核心修复】：如果输入的字符 c 恰好等于模式串的下一个字符（正常匹配）
                // 那么最长匹配长度直接就是 j + 1，无需再枚举后缀
                if (j < m && c == pattern.charAt(j)) {
                    maxLen = j + 1;
                } else {
                    // 否则，枚举所有真后缀长度 len: 1 .. j
                    for (int len = 1; len <= j; len++) {
                        String suffix = s.substring(s.length() - len);
                        String patPrefix = pattern.substring(0, len);
                        if (suffix.equals(patPrefix)) {
                            maxLen = len; // 从小到大枚举，最后留下的就是最长的
                        }
                    }
                }
                dfa[j][c] = maxLen;
            }
        }
        return dfa;
    }

    /**
     * 使用 DFA 进行字符串搜索，返回所有匹配的起始位置
     */
    public static List<Integer> search(String text, String pattern, int[][] dfa) {
        List<Integer> positions = new ArrayList<>();
        int m = pattern.length();
        int state = 0;

        for (int i = 0; i < text.length(); i++) {
            state = dfa[state][text.charAt(i)];
            if (state == m) {
                positions.add(i - m + 1);
                // 不重置 state，利用接受状态的出边继续搜索重叠匹配
            }
        }
        return positions;
    }

    // ==================== 测试 & 验证 ====================

    public static void main(String[] args) {
        String pattern = "ABAB";

        // 1. 两种方法构建 DFA
        int[][] dfaOptimized = buildDfa(pattern);
        int[][] dfaBruteForce = buildDfaBruteForce(pattern);

        // 2. 验证两者完全一致
        boolean match = true;
        for (int j = 0; j <= pattern.length(); j++) {
            for (int c = 0; c < 256; c++) {
                if (dfaOptimized[j][c] != dfaBruteForce[j][c]) {
                    System.out.printf("MISMATCH at state=%d, char='%c': opt=%d, brute=%d%n",
                            j, (char) c, dfaOptimized[j][c], dfaBruteForce[j][c]);
                    match = false;
                }
            }
        }
        System.out.println("Two DFAs identical: " + match);

        // 3. 打印可读转移表（仅 A/B/other）
        printDfa(dfaOptimized, pattern);

        // 4. 搜索测试
        String text = "ABABABABXABAB";
        List<Integer> hits = search(text, pattern, dfaOptimized);
        System.out.println("\nText:    \"" + text + "\"");
        System.out.println("Pattern: \"" + pattern + "\"");
        System.out.println("Matches at positions: " + hits);
    }

    private static void printDfa(int[][] dfa, String pattern) {
        int m = pattern.length();
        System.out.println("\n=== DFA Transition Table ===");
        System.out.printf("%-8s %-12s %-8s %-8s %-8s%n",
                "State", "Matched", "'A'", "'B'", "other");
        for (int j = 0; j <= m; j++) {
            String matched = j == 0 ? "\"\"" : "\"" + pattern.substring(0, j) + "\"";
            // other: 取一个不在 {A,B} 中的字符，比如 'C'
            int otherTarget = dfa[j]['C'];
            System.out.printf("q%-7d %-12s %-8d %-8d %-8d%n",
                    j, matched, dfa[j]['A'], dfa[j]['B'], otherTarget);
        }
    }
}