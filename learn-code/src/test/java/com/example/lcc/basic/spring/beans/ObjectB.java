package com.example.lcc.basic.spring.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectB {

    @Autowired
    ObjectA a;

    public ObjectB() {
        System.out.println("b has created.");
    }
}
