package com.example.lcc.algorithm.string;

import java.util.Formatter;

/**
 * @date 2018/4/4
 */
public class ContainsString {
    /**
     * 给定两个分别由字母组成的字符串A和字符串B，字符串B的长度比字符串A短。请问，如何最快地判断字符串B中所有字母是否都在字符串A里？
     * <p>
     * 为了简单起见，我们规定输入的字符串只包含大写英文字母，请实现函数bool StringContains(string &A, string &B)
     * <p>
     * 比如，如果是下面两个字符串：
     * <p>
     * String 1：ABCD
     * <p>
     * String 2：BAD
     * <p>
     * 答案是true，即String2里的字母在String1里也都有，或者说String2是String1的真子集。
     * <p>
     * 如果是下面两个字符串：
     * <p>
     * String 1：ABCD
     * <p>
     * String 2：BCE
     * <p>
     * 答案是false，因为字符串String2里的E字母不在字符串String1里。
     * <p>
     * 同时，如果string1：ABCD，string 2：AA，同样返回true。
     */

    //==============================解法一、逐字比较=========================================

    /**
     * 将b中的每个字符跟a中的字符比较，
     * 如果出现有没有匹配的 则返回false
     * @param a
     * @param b
     * @return
     */
    boolean StringContain_1(String a, String b) {
        char[] charsA = a.toCharArray();
        char[] charsB = b.toCharArray();
        for (int i = 0; i < charsB.length; i++) {
            for (int j = 0; (j < charsA.length && charsA[j] != charsB[i]); j++) {
                if (j >= charsB.length) {
                    return false;
                }
            }
        }
        return true;
    }
    //==============================解法一、逐字比较=========================================

    //==============================解法二、快速排序 + 比较=========================================
    //==============================解法二、快速排序 + 比较=========================================


}
