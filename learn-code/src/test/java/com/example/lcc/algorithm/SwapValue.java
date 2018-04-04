package com.example.lcc.algorithm;

import org.junit.Test;

/**
 * @date 2018/4/3
 */
public class SwapValue {

    @Test
    public void swap() {
        /**
         * 方法一、中间变量
         */
        int a = 2, b = 1;
        int c = a;
        a = b;
        b = c;

        /**
         * 方法二、加减法
         */
        int a1 = 2, b1 = 1;
        a1 = a1 + b1;
        b1 = a1 - b1;
        a1 = a1 - b1;
        /**
         * 方法三、乘除法
         * 注意 分子不能为 0
         */
        int a2 = 2, b2 = 2;
        a2 = a2 * b2;
        b2 = a2 / b2;
        a2 = a2 / b2;

        /**
         * 方法四、异或法
         */
        int a3 = 2, b3 = 2;
        a3 = a3 ^ b3;
        b3 = a3 ^ b3;
        a3 = a3 ^ b3;
        System.out.println(a3);

    }

}
