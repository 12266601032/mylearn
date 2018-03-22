package com.example.lcc.basic.guava;

import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by liucongcong on 2018/3/13.
 */
public class ReflectionDemo {

    @Test
    public void reflection() {
        //对 Proxy.newProxyInstance()的简单封装
        Reflection.newProxy(Say.class, new InvocationHandler() {
            private Object target;

            public InvocationHandler target(Object target) {
                this.target = target;
                return this;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before say.");
                return method.invoke(target, args);
            }
        }.target(new Say() {
            @Override
            public void hello() {
                System.out.println("hello!");
            }
        })).hello();
        //对Class.forName()的简单封装
        Reflection.initialize(Say.class);
        //com.google.common.ReflectDemo.Reflection = com.google.common.ReflectDemo
        System.out.println(Reflection.getPackageName(Say.class));

    }

    @Test
    public void typeResolver() throws Exception {




        TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {
        };
        TypeToken<?> typeToken1 = typeToken.resolveType(Long.class);
        System.out.println(typeToken1);
        System.out.println(typeToken.getRawType());


    }



    public static interface Say {
        void hello();
    }
}
