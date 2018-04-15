package com.example.lcc.basic.java.reflect.clazz;

import org.junit.Test;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ClassAPIDemo {
    @Test
    public void demo() {
        /**
         * Map.class.isAssignableFrom(cls)方法是判断Map是否为目标类的父类或者接口
         */
        System.out.println(HashMap.class.isAssignableFrom(Map.class)); //false
        System.out.println(Map.class.isAssignableFrom(HashMap.class)); //true
        System.out.println(Enum.class.isAssignableFrom(RoundingMode.class));//true
    }
}
