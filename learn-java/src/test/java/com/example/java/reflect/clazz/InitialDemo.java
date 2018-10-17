package com.example.java.reflect.clazz;

/**
 * @date 2018/3/30
 */
public class InitialDemo {

    /**
     * 静态代码块 按照顺序从上到下 依次加载
     */
    static {
        System.out.println("静态代码块-----1！");
    }
    static {
        System.out.println("静态代码块-----2！");
    }
    static {
        System.out.println("静态代码块-----3！");
    }

    /**
     * 构造代码块
     * 构造代码块会优先于构造方法执行，构造代码块执行顺序为从上到下 依次执行
     */
    public InitialDemo() {
        System.out.println("无参构造");
    }
    {
        System.out.println("构造代码块 ---- 1");
    }
    {
        System.out.println("构造代码块 ---- 2");
    }
    {
        System.out.println("构造代码块 ---- 3");
    }


    public static void main(String[] args) {
        System.out.println("指定main方法");
        new InitialDemo();
        System.out.println(null instanceof InitialDemo);
    }
}
