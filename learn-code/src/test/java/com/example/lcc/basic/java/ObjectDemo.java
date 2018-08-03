package com.example.lcc.basic.java;

import org.junit.Test;

public class ObjectDemo {

    @Test
    public void pointTest() {
        Object a = new Object();
        Object b = a;
        a = new Object();
        System.out.println(b == a);
    }
}
