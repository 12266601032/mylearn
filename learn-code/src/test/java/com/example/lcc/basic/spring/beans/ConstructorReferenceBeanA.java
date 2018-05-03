package com.example.lcc.basic.spring.beans;

public class ConstructorReferenceBeanA {
    ConstructorReferenceBeanB constructorReferenceBeanB;

    public ConstructorReferenceBeanA(ConstructorReferenceBeanB constructorReferenceBeanB) {
        this.constructorReferenceBeanB = constructorReferenceBeanB;
        System.out.println("ConstructorReferenceBeanA created...");
    }
}
