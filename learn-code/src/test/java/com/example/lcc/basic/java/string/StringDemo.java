package com.example.lcc.basic.java.string;

import org.junit.Test;

import java.util.Date;
import java.util.Locale;

/**
 * @date 2018/3/27
 */
public class StringDemo {

    /**
     * String.format()用法
     * 1、转换符
     * %s: 字符串类型，如："ljq"
     * %b: 布尔类型，如：true
     * %d: 整数类型(十进制)，如：99
     * %f: 浮点类型，如：99.99
     * %%: 百分比类型，如：％
     * %n: 换行符
     * <p>
     * %h 转换为十六进制
     */
    @Test
    public void format() {
        //进制表示
        int a = 0x2f; //小写十六进制（等价于0x002f）
        int b = 0x2F;//大写十六进制
        int c = 10;//标准十进制
        int d = 010;//以零开头，表示八进制
        /*
         *
         * %[argument_index$][flags][width][.precision]conversion语法
         * %[argument_index$][flags][width]conversion 日期时间
         * %[flags][width]conversion 无参
         *
         *
         */
        String rt = null;
        //等于是new Formatter().format(format, args).toString()
        rt = String.format("Hi,%S.", "lili"); //格式化字符串
        System.out.println(rt);
        //等于是new Formatter((Appendable).format(Locale.getDefault(), format, args)
        //两者背后都是使用Formatter进行格式化字符串的。
        System.out.printf("小写字符:%s.%n", "string");
        System.out.printf("大写字符:%S.%n", "string"); //%S等于是将参数String#toUpperCase()了一下
        System.out.printf("十六进制转换:%h.%n", 161); //a1
        System.out.printf("Unicode字符:%c.%n", 'a');
        System.out.printf("结果被格式化为十进制整数:%d.%n",0x000f);
        System.out.printf("结果被格式化为八进制整数:%o.%n",0x000f);
        System.out.printf("结果被格式化为十六进制整数:%x.%n",15);
        System.out.printf("结果被格式化为十进制科学计数法:%e.%n",15.001f);
        System.out.printf("结果被格式化为十进制数:%f.%n",0.05999999f);



    }
}
