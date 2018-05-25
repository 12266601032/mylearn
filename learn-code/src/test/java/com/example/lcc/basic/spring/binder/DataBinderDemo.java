package com.example.lcc.basic.spring.binder;

import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DataBinderDemo {

    @Test
    public void binder() {
        Foo target = new Foo();
        DataBinder binder = new DataBinder(target);
        binder.setValidator(new FooValidator());
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("age",17));
        binder.bind(propertyValues);

        BindingResult bindingResult = binder.getBindingResult();

    }

    class FooValidator implements Validator{

        @Override
        public boolean supports(Class<?> clazz) {
            return false;
        }

        @Override
        public void validate(Object target, Errors errors) {

        }
    }

    class Foo {

       private Integer age;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
