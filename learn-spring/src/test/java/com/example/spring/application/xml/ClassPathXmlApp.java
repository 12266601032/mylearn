package com.example.spring.application.xml;

import com.example.spring.beans.ObjectA;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ClassPathXmlApp {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        applicationContext.start();
        System.out.println(applicationContext.getBean("objectA", ObjectA.class).propertyValue);
        byte[] bytes = new byte[2];

        StringBuilder sb = new StringBuilder();
        int len;
        while ((len = System.in.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len, "utf-8"));
            if (sb.indexOf("\n") != -1) {
                sb.deleteCharAt(sb.length() - 1);
                System.out.println(sb.toString());
                if (sb.toString().equals("exit")) {
                    System.exit(1);
                }
                sb.delete(0, sb.length());
            }
        }

    }
}
