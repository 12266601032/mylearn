package com.example.java.invoke;

import org.junit.Test;

import java.lang.invoke.MethodHandles;

/**
 * @date 2018/4/8
 */
public class MethodHanldesDemo {

    @Test
    public void lookup() throws Exception {

        //获取当前类 可用于log4j来构造Log对象
        //private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
        System.out.println(MethodHandles.lookup().lookupClass());
    }
}
