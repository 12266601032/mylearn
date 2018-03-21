package com.example.lcc.basic.guava;

import com.google.common.collect.Lists;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeResolver;
import com.google.common.reflect.TypeToken;
import org.junit.Test;
import org.omg.CORBA.Any;

import javax.lang.model.type.TypeVisitor;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        //com.google.common.reflect.Reflection = com.google.common.reflect
        System.out.println(Reflection.getPackageName(Say.class));

    }

    @Test
    public void typeResolver(){
        TypeResolver typeResolver = new TypeResolver();
        AnyType<Long> stringAnyType = new AnyType<>();


        TypeVariable<? extends Class<? extends AnyType>>[] typeParameters = stringAnyType.getClass().getTypeParameters();
        TypeVariable t = typeParameters[0];
        System.out.println(t);
        System.out.println(t.getBounds().length);
        System.out.println(Arrays.toString(t.getBounds()));
        System.out.println(t instanceof ParameterizedType);

        TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {};
        TypeToken<?> typeToken1 = typeToken.resolveType(Long.class);
        System.out.println(typeToken1);
        System.out.println(typeToken.getRawType());
        System.out.println(typeResolver.resolveType(typeToken.getType()));

        TypeToken<AnyType<Long>> typeToken2 = new TypeToken<AnyType<Long>>() {};
        System.out.println(((ParameterizedType)typeToken2.getSupertype(AnyType.class).getType()).getActualTypeArguments()[0]);

    }

    public static class AnyType<T extends Serializable>{

    }

    public static class SubType<Long> extends AnyType{

    }



    public static interface Say {
        void hello();
    }
}
