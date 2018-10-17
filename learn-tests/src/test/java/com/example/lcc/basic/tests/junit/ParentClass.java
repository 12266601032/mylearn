package com.example.lcc.basic.tests.junit;

import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @date 2018/3/20
 */
public class ParentClass {
    @Before
    public void parentSetup(){
        System.out.println("parentSetup is running...");
    }
    @BeforeClass
    public static void parentStaticSetup(){
        System.out.println("ParentClass parentStaticSetup is running..");
    }
}
