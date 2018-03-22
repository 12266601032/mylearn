package com.example.lcc.basic.java.reflect;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liucongcong
 * @date 2018/3/22
 */
public class ReflectDemo {

    /**
     * java的Type接口有4个关于泛型的子接口
     * <ul>
     * <li>
     * 1. {@link java.lang.reflect.GenericArrayType}
     * 表示泛型数组类型
     * <p>
     * </li>
     * <li>
     * 2. {@link java.lang.reflect.TypeVariable}
     * 表示泛型变量 T
     * <p>
     * </li>
     * <li>
         * 3. {@link java.lang.reflect.ParameterizedType}
     * 表示参数化了的泛型类型 {@code List<String>}
     * <p>
     * </li>
     * <li>
     * 4. {@link java.lang.reflect.WildcardType}
     * 表示泛型统配符类型  {@code ?}, {@code ? extends Number}, or {@code ? super Integer}
     * <p>
     * </li>
     * </ul>
     * @see com.example.lcc.basic.java.reflect.clazz.GotClassDemo
     */
    @Test
    public void type() throws Exception {


        //获取泛型参数类型
        TypeVariable<? extends Class<? extends TypeVariableDemo>>[] typeParameters = TypeVariableDemo.class.getTypeParameters();
        System.out.printf("TypeVariableDemo<T extends Number & Serializable>泛型参数个数：%d.%n", typeParameters.length);
        TypeVariable t = typeParameters[0];
        System.out.printf("TypeVariableDemo<T extends Number & Serializable>泛型参数名称：%s.%n", t.getTypeName());
        System.out.printf("ParameterizedTypeDemo<Long>泛型参数属于参数化类型：%b.%n", t instanceof java.lang.reflect.ParameterizedType);
        System.out.printf("ParameterizedTypeDemo<Long>泛型参数属于参数化类型：%b.%n", t instanceof TypeVariable);
        //t.getBounds() 获取泛型上边界，如果没有声明就是Object
        System.out.printf("TypeVariableDemo<T extends Number & Serializable>泛型边界个数：%d.%n", t.getBounds().length);
        System.out.printf("TypeVariableDemo<T extends Number & Serializable>泛型边界参数：%s.%n", Arrays.toString(t.getBounds()));
        //这里泛型边界类型属于Class对象
        System.out.printf("TypeVariableDemo<T extends Number & Serializable>泛型边界参数为字节码对象：%b.%n", t.getBounds()[1] instanceof Class);



        //参数化的泛型类型
        TypeVariable<? extends Class<? extends ParameterizedTypeDemo>>[] typeParameters1 = ParameterizedTypeDemo.class.getTypeParameters();
        System.out.printf("ParameterizedTypeDemo<Long>泛型参数个数：%d.%n", typeParameters1.length);
        TypeVariable t1 = typeParameters1[0];
        System.out.printf("ParameterizedTypeDemo<Long>泛型参数名称：%s.%n", t1.getTypeName());
        System.out.printf("ParameterizedTypeDemo<Long>泛型参数属于参数化类型：%b.%n", t1 instanceof java.lang.reflect.ParameterizedType);
        //t.getBounds() 获取泛型上边界，如果没有声明就是Object
        System.out.printf("ParameterizedTypeDemo<Long>泛型边界个数：%d.%n", t1.getBounds().length);
        System.out.printf("ParameterizedTypeDemo<Long>泛型边界参数：%s.%n", Arrays.toString(t1.getBounds()));
        //这里泛型边界类型属于Class对象
        System.out.printf("ParameterizedTypeDemo<Long>泛型边界参数为字节码对象：%b.%n", t1.getBounds()[0] instanceof Class);

        //获取数组泛型
        Type genericArrayType = GenericArrayTypeDemo.class.getMethod("create", Object[].class).getGenericReturnType();
        System.out.printf("T[]属于GenericArrayType：%b.%n", genericArrayType instanceof GenericArrayType);
        GenericArrayType arrayType = (GenericArrayType) genericArrayType;
        System.out.printf("T[]的元素泛型类型属于泛型变量：%b.%n", arrayType.getGenericComponentType() instanceof TypeVariable);

        //泛型通配符
        Type type = WildcardTypeDemo.class.getMethod("createList").getGenericReturnType();
        System.out.printf("List<?>的泛型类型属于统配符：%b.%n", ((ParameterizedType) type).getActualTypeArguments()[0] instanceof WildcardType);


    }

    public static class GenericArrayTypeDemo {
        public static <T> T[] create(T[] t) {
            return t;
        }
    }

    public static class TypeVariableDemo<T extends Number & Serializable> {
    }

    public static class ParameterizedTypeDemo<Long> {
    }

    public static class WildcardTypeDemo {
        public static List<?> createList() {
            return new ArrayList<>();
        }
    }
}
