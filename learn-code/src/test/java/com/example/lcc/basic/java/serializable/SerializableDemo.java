package com.example.lcc.basic.java.serializable;

import lombok.Data;
import org.junit.Test;

import java.io.*;
import java.util.Date;

/**
 * @date 2018/3/23
 */
public class SerializableDemo {
    @Test
    public void serialize() throws Exception {
        //创建一个字节数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //创建对象输出流
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        Person person = new Person();
        person.setName("lili");
        person.setAge(22);
        person.setTransientField("hello");


        SinglePerson instance = SinglePerson.getInstance();
        SinglePerson innerSingleInstance = SinglePerson2.getInstance();

        System.out.println("=======================开始序列化对象======================");

        //写入字符串常量
        objectOutputStream.writeObject("序列化字符串");
        //写入匿名对象
        objectOutputStream.writeObject(new Date());
        //写入对象
        objectOutputStream.writeObject(person);
        //写入单例对象
        objectOutputStream.writeObject(instance);
        objectOutputStream.writeObject(innerSingleInstance);
        objectOutputStream.close();

        Person.staticField = "修改了的静态变量";
        System.out.println("=======================开始反序列化对象======================");
        //反序列化
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        Object o1 = objectInputStream.readObject();
        System.out.printf("反序列化字符串的结果：%s。%n", o1);
        Object o2 = objectInputStream.readObject();
        System.out.printf("反序列化匿名日期对象的结果：%s。%n", o2);
        Person o3 = (Person) objectInputStream.readObject();
        System.out.printf("反序列化Person的结果：%s。静态变量值：%s。transient成员的值：%s。%n", o3, o3.staticField, o3.getTransientField());
        Object o4 = objectInputStream.readObject();
        Object o5 = objectInputStream.readObject();
        objectInputStream.close();

        System.out.printf("单例对象反序列化后是否为同一对象：%b.%n", o4 == instance);
        System.out.printf("单例对象反序列化后是否为同一对象：%b.%n", o5 == innerSingleInstance);
        /*
         * 1.序列化并不保存静态变量
         * 先序列化对象然后修改静态变量值
         * 可以看到反序列化的结果静态变量是改变了的，这是因为静态变量跟class相关属于类的状态而与实例无关，
         * 序列化的是实例。因此序列化并不保存静态变量。
         * 2.transient关键字修饰的字段不会被序列化
         * 3.单例对象反序列化后是一个新的对象。
         * 4.反序列化并不是通过构造器来实例化对象的。
         */

    }

    public static class SinglePerson implements Serializable {

        private static final SinglePerson singlePerson = new SinglePerson();

        public static SinglePerson getInstance() {
            return singlePerson;
        }
    }

    public static class SinglePerson2 implements Serializable {


        private static class InnerClassHolder {
            private static final SinglePerson singlePerson = new SinglePerson();
        }

        public static SinglePerson getInstance() {
            return InnerClassHolder.singlePerson;
        }
    }

    @Data
    public static class Person implements Serializable {
        private String name;
        private transient String transientField;
        private Integer age;
        static String staticField = "original";

        public Person() {
            System.out.println("Person无参构造...");
        }
    }
}
