package com.example.spring.test.annotation;

import java.lang.annotation.*;

/**
 * @author liucongcong
 * @date 2018/4/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ImportMockBeans {

    Class<?>[] value() default {};
}
