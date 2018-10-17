package com.example.java.string;

import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

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
     * %h: 转换为十六进制
     * %f: 浮点类型，如：99.99
     * %%: 百分比类型，如：％
     * %n: 换行符
     * <p>
     * 转换符使用%大写形式 输出的参数将被格式化为大写字符
     * 如果接受参数为纯数字类型则 没有对应大写转换符的
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
         * width是参数最少输出字符个数
         *
         */
        String rt = null;
        //下面两者背后都是使用Formatter进行格式化字符串的。
        rt = String.format("Hi,%S.", "lili"); //格式化字符串 等于是new Formatter().format(format, args).toString()
        System.out.println(rt);//等于是new Formatter(Appendable).format(Locale.getDefault(), format, args)

        //%s: 字符串类型
        System.out.printf("小写字符:%s，大写字符：%S.%n", "string", "string");//小写字符:string，大写字符：STRING.
        //%b: 布尔类型
        System.out.printf("布尔值：%b,大写%B.%n", true, false); //布尔值：true,大写FALSE.
        //%h: 转换为十六进制
        System.out.printf("十六进制转换:%h，%H.%n", 161, 161); //十六进制转换:a1，A1.
        //%c: 字符类型
        System.out.printf("Unicode字符:%c,%C.%n", 'a', 'a');
        //%d: 整数类型(十进制)，如：99
        System.out.printf("结果被格式化为十进制整数:%d.%n", 0x000f);
        //%o: 八进制
        System.out.printf("结果被格式化为八进制整数:%o.%n", 0x000f);
        //%x: 十六进制
        System.out.printf("结果被格式化为十六进制整数:%x,%X.%n", 15, 15);
        //%e: 科学计数法表示
        System.out.printf("结果被格式化为十进制科学计数法:%e，%E.%n", 15.001f, 15.001f);
        //%f: 浮点类型
        System.out.printf("结果被格式化为十进制数:%f.%n", 0.05999999f);
        //到底是十进制还是科学计数法取决于值的带下，如果(0.0001)10^-4<=值<10^精度 如下就是小于0.0001 因此会使用科学计数法表示
        System.out.printf("结果使用科学计数法或十进制格式进行格式化，这取决于四舍五入后的精度和值:%.2g.%n", 0.00001f);
        System.out.printf("结果被格式化为带有效位数和指数的十六进制浮点数:%a.%n", 10000000f);
        //%%: '%'号
        System.out.printf("结果是一个'%%'符号 :%%"); // 由于'%'是语法中的字符，要输出'%' 要通过%%的形式
        //%n: 换行符
        System.out.printf("输出换行符：%n.%n"); // 相当于通过System.getProperty("line.separator")获得的 特定于平台的换行符


        /**
         * 日期格式转换（组合转换符）
         */
        System.out.printf("日期转换：%tR %n", new Date()); //等于 HH:mm
        System.out.printf("日期转换：%tT %n", new Date()); //等于 HH:mm:ss
        System.out.printf("日期转换：%tr %n", new Date()); //等于 HH:mm:ss 上午/下午
        System.out.printf("日期转换：%tD %n", new Date()); //等于 mm/dd/yy
        System.out.printf("日期转换：%tF %n", new Date()); //等于 yyyy-MM-dd
        System.out.printf("日期转换：%tc %n", new Date()); //"Sun Jul 20 16:17:00 EDT 1969".
        new Formatter(System.out).format(Locale.getDefault(), "日期转换：%tF %n", new Date());

        /**
         * flags
         */
        //左对齐 参数最少输出10个字符 不够补" "
        System.out.printf("第一列：%-10s，第二列：%-10s，第三列：%-10s; %n", "a", "b", "c");
        System.out.printf("第一列：%-10s，第二列：%-10s，第三列：%-10s; %n", "aaaaaa", "baaaaaa", "caaaaa");
        //仅用于数字和浮点 前补0
        System.out.printf("数字：%010d %n", 1);
        //输出的数字总是带符号，正数也带符号
        System.out.printf("数字：%+10d %n", 1);
        //四舍五入 保留2位小数 小数点不够补0
        System.out.printf("浮点数：%1$.2f %n", 0.124); //0.12
        System.out.printf("浮点数：%1$.2f %n", 0.125); //0.13
        System.out.printf("浮点数：%1$.2f %n", 0.1);   //0.10
        System.out.printf("浮点数：%1$.2f %n", new BigDecimal(100));   //100.00


        /**
         * 索引
         * 1、显式索引
         *      指定参数索引 1$ 2$ .. 从1 开始
         * 2、相对索引
         *      使用上一个引用
         * 3、普通索引
         *      不指定,会根据是第几个来选择引用 %s %s %s 等于 %1$s %2$s %3$s
         */
        System.out.printf("显式索引:%1$s,%2$s,%3$s,%2$s,%1$s %n", "idx1", "idx2", "idx3");
        System.out.printf("相对索引:%s %s %<s %<s %n", "a", "b", "c", "d"); //a b b b
        System.out.printf("普通索引:%s %s %s %s %n", "a", "b", "c", "d"); //a b c d

        //组合使用
        System.out.printf("组合使用：%s %2$s %<s %s %s %n", "a", "b", "c", "d"); //a b b b c


        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.parse("", new ParsePosition(1));

        Date xxx = Optional.ofNullable((Date) null).orElseThrow(createNullExcp("xxx"));
        System.out.println(xxx);

    }

    @Test
    public void test2() throws ParseException {
        System.out.printf("{} array {} %n","a","b"); //不支持这种
        System.out.printf("%d %n",new Integer(2));
        System.out.printf("百分比：%s.",new DecimalFormat("0.00%").format(0.1111));
        System.out.println(new DecimalFormat("0.00%").parse("0.5%"));
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        decimalFormat.setParseBigDecimal(true);

        System.out.println(decimalFormat.parse("0.500005%"));
        System.out.println(new BigDecimal("0.00500005"));
        System.out.println(BigDecimal.valueOf(new DecimalFormat("0.00%").parse("0.5%").doubleValue()));
        System.out.println(NumberFormat.getPercentInstance().parse("0.05%"));
    }

    /**
     *
     * configClass3
     * ------------
     * configClass2
     * ------------
     * configClass1
     * ------------
     *
     *
     *
     *
     *
     */
    @Test
    public void testSub() {
        String type = "101";
        System.out.println(type == null || type.length() < 2 ? "01" : type.substring(type.length() - 2, type.length()));
    }

    private Supplier<? extends RuntimeException> createNullExcp(String s) {
        return () -> new NullPointerException(s);
    }
}
