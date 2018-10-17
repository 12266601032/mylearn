package com.example.java.reflect;

import com.example.java.reflect.clazz.GotClassDemo;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @date 2018/3/22
 */
public class GenericDemo {

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
     *
     * @see GotClassDemo
     */
    @Test
    public void type() throws Exception {


        //获取泛型参数类型
        TypeVariable<? extends Class<? extends TypeVariableDemo>>[] typeParameters = TypeVariableDemo.class.getTypeParameters();
        System.out.printf("TypeVariableDemo<T extends Number & Serializable>泛型参数个数：%d.%n", typeParameters.length);
        TypeVariable t = typeParameters[0];
        System.out.printf("TypeVariableDemo<T extends Number & Serializable>泛型参数名称：%s.%n", t.getTypeName());
        System.out.printf("ParameterizedTypeDemo<T>泛型参数属于参数化类型：%b.%n", t instanceof java.lang.reflect.ParameterizedType);
        System.out.printf("ParameterizedTypeDemo<T>泛型参数属于参数化类型：%b.%n", t instanceof TypeVariable);
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

    @Test
    public void genericArrayType() throws NoSuchMethodException {
        Method createMethod = GenericArrayTypeDemo.class.getMethod("create", Object[].class);
        //获取方法的泛型参数类型
        Type[] genericParameterTypes = createMethod.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            assertTrue("(T[] t) 参数t的泛型类型属于GenericArrayType类型", genericParameterType instanceof GenericArrayType);
            GenericArrayType type = (GenericArrayType) genericParameterType;
            System.out.println("泛型类型名称：" + type.getTypeName() + "" + type.getGenericComponentType());
            assertTrue("泛型数组的元素泛型类型为泛型变量TypeVariable",
                    type.getGenericComponentType() instanceof TypeVariable);
        }
    }

    public static class GenericArrayTypeDemo {
        public static <T> T[] create(T[] t) {
            return t;
        }
    }


    @Test
    public void typeVariable() {
        //泛型变量可以指定泛型边界,只能指定泛型上界
        TypeVariable<Class<TypeVariableDemo>>[] typeParameters = TypeVariableDemo.class.getTypeParameters();
        assertEquals("泛型变量个数", 2, typeParameters.length);
        for (TypeVariable<Class<TypeVariableDemo>> typeParameter : typeParameters) {
            System.out.println("泛型变量名称：" + typeParameter.getName());
        }
        Type[] bounds = typeParameters[0].getBounds();
        Class<TypeVariableDemo> genericDeclaration = typeParameters[0].getGenericDeclaration();
        assertEquals("GenericDeclaration为该泛型参数所在的类", TypeVariableDemo.class, genericDeclaration);
        assertEquals("泛型边界个数", 2, bounds.length);
        assertSame(bounds[0], Number.class);
        assertSame(bounds[1], Serializable.class);

        bounds = typeParameters[1].getBounds();
        assertEquals(1, bounds.length);
        assertTrue("T的泛型上界为泛型变量", bounds[0] instanceof TypeVariable);
        assertSame("T的泛型上界为泛型变量D", bounds[0], typeParameters[0]);

    }

    public static class TypeVariableDemo
            <D extends Number & Serializable,
                    T extends D> {
    }

    @Test
    public void parameterizedType() throws NoSuchMethodException {

        Method parameterized = ParameterizedTypeDemo.class.getMethod("parameterized", ParameterizedTypeDemo.class);
        Type[] genericParameterTypes = parameterized.getGenericParameterTypes();
        Type type = genericParameterTypes[0];
        assertTrue(type instanceof ParameterizedType);
        ParameterizedType parameterizedType = (ParameterizedType) type;
        //获取实际参数类型
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        assertSame(actualTypeArguments[0], String.class);
        //获取泛型类、接口
        Type rawType = parameterizedType.getRawType();
        assertSame(rawType, ParameterizedTypeDemo.class);

        Type ownerType = parameterizedType.getOwnerType();
        assertSame(ownerType, GenericDemo.class);

    }

    public static class ParameterizedTypeDemo<T> {

        public void parameterized(ParameterizedTypeDemo<String> param) {

        }
    }


    @Test
    public void wildcardType() throws NoSuchMethodException {

        Method createList = WildcardTypeDemo.class.getMethod("superType", List.class, Long.class);
        Type[] types = createList.getGenericParameterTypes();
        assertEquals("参数个数", 2, types.length);
        ParameterizedType type = (ParameterizedType) types[0];
        System.out.println(type.getTypeName());
        Type[] actualTypeArguments = type.getActualTypeArguments();
        assertTrue("", actualTypeArguments[0] instanceof WildcardType);
        WildcardType tp = (WildcardType) actualTypeArguments[0];
        Type[] lowerBounds = tp.getLowerBounds();
        assertEquals(1, lowerBounds.length);
        assertSame(Number.class, lowerBounds[0]);
    }

    public static class WildcardTypeDemo {
        public static void superType(List<? super Number> list, Long id) {
        }

        public static List<?> createList() {
            return new ArrayList<>();
        }
    }
}
