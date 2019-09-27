package com.example.java;

import org.junit.Test;

public class ObjectDemo {

    @Test
    public void pointTest() {
        Object a = new Object();
        Object b = a;
        a = new Object();
        System.out.println(b == a);
    }


    @Test
    public void testSeq() {
        /*
         * 父子类关系构造对象各代码块的执行顺序：
         * 1. 先执行父类的静态代码块
         * 2. 执行子类的静态代码块
         * 3. 执行父类的构造代码块
         * 4. 执行父类中的构造器
         * 5. 执行子类的构造代码块
         * 6. 执行子类的构造器
         */
        new Child();
    }

    public static class Parent {
        static {
            System.out.println("1.Parent类中的静态代码块！");
        }

        {
            System.out.println("3.Parent类中的实例代码块！");
        }

        public Parent() {
            System.out.println("4.Parent类中的构造方法！");
        }
    }

    public static class Child extends Parent {
        static {
            System.out.println("2.Child类中的静态代码块！");
        }

        {
            System.out.println("5.Child类中的实例代码块！");
        }

        public Child() {
            System.out.println("6.Child类中的构造方法！");
        }
    }
}
