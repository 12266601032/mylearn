package com.example.java.reflect.clazz;

import org.junit.Test;

/**
 * @date 2018/3/22
 */
public class GotClassDemo {

    /**
     * java中获取class对象的三种方法
     * 一、根据实例获取class对象
     * GotClassDemo instance =  new GotClassDemo();
     * Class<? extends GotClassDemo> aClass = instance.getClass();
     * 二、根据类名.class获取class对象
     * Class<GotClassDemo> aClass = GotClassDemo.class;
     * 三、根据全路径类名获取class对象
     * Class<?> aClass = Class.forName("GotClassDemo");
     *
     *
     * 可以根据Class的泛型信息看的出，通过实例获取的class对象有可能是子类对象的所以使用的是Class<? extends GotClassDemo>
     * 通过类名.class获取的一定是当前类的class对象所以是Class<GotClassDemo>
     * 通过string类型的全路径类名取的class对象没有类型信息所有使用的是Class<?>
     *
     */
    @Test
    public void clazz() throws Exception {
        GotClassDemo instance =  new GotClassDemo();
        Class<? extends GotClassDemo> aClass1 = instance.getClass();
        Class<GotClassDemo> aClass2 = GotClassDemo.class;
        Class<?> aClass3 = Class.forName("com.example.java.reflect.clazz.GotClassDemo");

        /**
         * 获取基本数据类型的class对象
         */
        Class<Byte> byteClass = byte.class;
        Class<Short> shortClass = short.class;
        Class<Integer> integerClass = int.class;
        Class<Long> longClass = long.class;
        Class<Character> characterClass = char.class;
        Class<Float> floatClass = float.class;
        Class<Double> doubleClass = double.class;
        Class<Boolean> booleanClass = boolean.class;

        /**
         * void的class对象
         */
        Class<Void> voidClass = void.class;

        /**
         * 数组的class对象
         */
        Class<Object[]> aClass = Object[].class;

        /**
         * 基本数据类型的class与它们对应的包装数据类型中的TYPE一一对应
         */
        System.out.println(Integer.TYPE == int.class); //true
        System.out.println(aClass2.getMethod("voidMethod").getReturnType() == Void.TYPE); // true

        /**
         * 需要注意的是以上class对象与包装类型.class不一个概念，
         * 并不能用来创建对象。
         */
    }

    public void voidMethod(){

    }
}
