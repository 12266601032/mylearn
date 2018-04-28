package com.example.lcc.basic.java.reflect.clazz;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @date 2018/4/24
 */
public class ClassAPIDemo {


    @Test
    public void testAssignable(){
        System.out.println(BigDecimal.class.isAssignableFrom(null)); //NullPointerException
    }

}
