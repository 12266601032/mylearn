package com.example.spring.application.xml;

import com.example.spring.beans.ConstructorReferenceBeanB;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class CircleReferenceClassPathXmlApp {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:circleReferenceBean.xml");
        applicationContext.start();
        applicationContext.getBean(ConstructorReferenceBeanB.class);
        System.in.read();
    }
}
