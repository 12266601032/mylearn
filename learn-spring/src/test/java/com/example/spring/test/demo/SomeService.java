package com.example.spring.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomeService {

    @Autowired
    MessageSupplier messageSupplier;

    public SomeService() {
        System.out.println("111");
    }

    public void printMessage() {
        System.out.println(messageSupplier.getMessage());
    }

}
