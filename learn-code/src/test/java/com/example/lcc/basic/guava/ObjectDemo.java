package com.example.lcc.basic.guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import lombok.Data;
import org.junit.Test;

/**
 * Created by liucongcong on 2018/3/14.
 */
public class ObjectDemo {


    @Test
    public void comparisonChain() {
        /**
         * ComparisonChain执行一种懒比较：它执行比较操作直至发现非零的结果，
         * 在那之后的比较输入将被忽略。
         */
        int i = ComparisonChain.start()
                .compare(1.1, 2.2)
                .compare(2, 1)
                .compare("b", "a")
                .result();
        System.out.println(i);

        /*
         * MoreObjects.toStringHelper(this)
         * 更方便的将对象进行toString处理
         */
        String s = new Person().toString();
        System.out.println(s);
    }


    public class Person{

        private int age;
        private String name;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("age",this.age)
                    .add("name",this.name)
                    .toString();
        }
    }
}
