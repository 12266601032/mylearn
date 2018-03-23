package com.example.lcc.basic.junit;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

import java.util.ArrayList;

/**
 * @date 2018/3/20
 */
public class JunitDemo extends ParentClass{

    /**
     * 使用@Before可以标识该方法会在其他@Test方法之前执行
     * 如果继承的父类中也@Before注解方法 并且子类中不是覆盖的关系的话
     * 父类中的@Before方法会先执行
     * 每个@Test方法的运行都会产生一次@Before方法的运行
     */
    @Before
    public void setup(){
        System.out.println("JunitDemo setup method is running..");
    }

    /**
     * 这个注解配合静态无参方法使用，保证在一次单元测试周期中只会运行一次
     * 父类中带@BeforeClass的方法会优先于子类运行，除非子类中有同样方法名的@BeforeClass注解的静态方法
     */
    @BeforeClass
    public static void staticSetup(){
        System.out.println("JunitDemo staticSetup is running..");
    }

    @Test
    public void simple1(){
        Assert.assertTrue(new ArrayList<>().isEmpty());
    }


    /**
     * expected参数默认是Test.None
     * 可以使用{@link org.junit.rules.ExpectedException} 来替代
     */
    @Test(expected = IllegalArgumentException.class)
    public void simple2(){
        throw new IllegalArgumentException();
    }


    @Rule
    public ExpectedException thrown =  ExpectedException.none();
    @Test
    public void simple2Instead() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        throw new IllegalArgumentException();
    }

    /**
     * 设置超时时间 毫秒值
     * 默认为 0 永不超时
     * 可以使用{@link org.junit.rules.Timeout} 来替代
     */
    @Test(timeout = 1000)
    public void simple3() throws InterruptedException {
        Thread.sleep(100);
    }

    @Rule
    public Timeout globalTimeout= Timeout.millis(1000);
    @Test
    public void simple3Instead() throws Exception {
        Thread.sleep(100);
    }
}
