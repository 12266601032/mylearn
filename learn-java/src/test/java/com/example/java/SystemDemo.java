package com.example.java;

import org.junit.Test;

import java.util.Map;
import java.util.Properties;

/**
 * @date 2018/4/9
 */
public class SystemDemo {

    @Test
    public void runtime() throws Exception {
        //系统环境变量值 可以获取到通过shell中导入的一些环境变量 或者系统本身设置的环境变量
        Map<String, String> getenv = System.getenv();
        System.out.println(getenv);
        //Java进程变量值 通过命令行参数的"-D"选项
        Properties properties = System.getProperties();
        System.out.println("***************************************************");
        System.out.println(properties);
    }
}
