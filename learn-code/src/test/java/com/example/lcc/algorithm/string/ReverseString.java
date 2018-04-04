package com.example.lcc.algorithm.string;

import org.junit.Test;

/**
 * @date 2018/4/3
 */
public class ReverseString {

    /**
     * 给定一个字符串，要求把字符串前面的若干个字符移动到字符串的尾部，
     * 如把字符串“abcdef”前面的2个字符'a'和'b'移动到字符串的尾部，使得原字符串变成字符串“cdefab”。
     * 请写一个函数完成此功能，要求对长度为n的字符串操作的时间复杂度为 O(n)，空间复杂度为 O(1)。
     */
    @Test
    public void rotating() {
        //暴力位移法
        System.out.println(leftRotateString_1("abcdef", 2));
        System.out.println(leftRotateString_2("abcdef", 2));


    }

    //==============================解法一、暴力位移法=========================================

    /**
     * 将前m个字符移到字符串尾部
     * 时间复杂度和空间复杂度：
     * 如果将m个字符移动到尾部 那么需要 m * n次操作
     * 时间复杂度为O(m * n)，空间复杂度为O(1)，
     *
     * @param string
     * @param m
     * @return
     */
    String leftRotateString_1(String string, int m) {
        char[] chars = string.toCharArray();
        while (m-- > 0) {
            leftShiftOne(chars);
        }
        return new String(chars);
    }

    /**
     * 将首位字符移动到最后一位
     *
     * @param chars
     * @return
     */
    char[] leftShiftOne(char[] chars) {
        char t = chars[0];  //保存第一个字符
        /**
         * 将一个字符移到最后一位
         *
         * 首先记录第一个字符
         * char first = chars[0];
         *
         * a b c d e
         * 1.第一次循环 i = 1
         * b b c d e   chars[0] = chars[1]
         * 2.第二次     i = 2
         * b c c d e   chars[1] = chars[2]
         * 3.第三次     i = 3
         * b c d d e   chars[2] = chars[3]
         * 4.第四次     i = 4
         * b c d e e   chars[3] = chars[4]
         *
         * 最后将第一位字符 放到最后一位
         * chars[chars.length - 1] = first;
         */
        for (int i = 1; i < chars.length; i++) {
            chars[i - 1] = chars[i];
        }
        chars[chars.length - 1] = t;
        return chars;
    }

    //==============================解法一、暴力位移法=========================================


    //==============================解法二、三步反转法=========================================

    /**
     * 反转数组中的字符
     * from从0开始
     * to为数组下标
     *
     * @param chars 字符数组
     * @param from  从哪开始
     * @param to    到哪结束
     */
    void reverse(char[] chars, int from, int to) {
        /*
         * from = 0, to = 4
         * 字符 a b c d e
         * 循环次数 = 5 / 2 = 2
         * 1. i = 0, j = 4
         * chars[0] = chars[4]
         * e b c d a
         * 2. i = 1, j = 3
         * chars[1] = chars[3]
         * e d c b a
         */
        while (from < to) {
            char ci = chars[from];
            chars[from++] = chars[to];
            chars[to--] = ci;
        }
    }


    /**
     * 字符串"abcdef" 如果需要将 ab 移动要末尾
     * 可以将它分为两个部分 X = "ab" Y = "cdef" 最后要的结果就是YX
     * X^T = ab^T = ba
     * Y^T = cdef^T = fedc
     * (X^TY^T)^T = YX =cdefab
     */
    String leftRotateString_2(String string, int m) {
        char[] chars = string.toCharArray();
        //first ~ m-1
        reverse(chars, 0, m - 1);
        //m ~ last
        reverse(chars, m, chars.length - 1);
        //first ~ last
        reverse(chars, 0, chars.length - 1);
        return new String(chars);
    }


    //==============================解法二、三步反转法=========================================
}
