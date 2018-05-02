package com.example.lcc.basic.spring.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectA {
    @Autowired
    public ObjectB objectB;

    public ObjectB propertyValue;

    public ObjectA() {
        System.out.println(" a has created.");
    }

    public ObjectB getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(ObjectB propertyValue) {
        this.propertyValue = propertyValue;
    }
}
