package com.example.spring.application.annotation;

import com.example.spring.beans.ObjectA;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@ComponentScan(basePackages = {"com.example.lcc.basic.spring.beans"})
public class AnnotationApp {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AnnotationApp.class);
        applicationContext.start();
        System.out.println(applicationContext.getBean(ObjectA.class).objectB);
        System.out.println(System.in.read());
    }
}
