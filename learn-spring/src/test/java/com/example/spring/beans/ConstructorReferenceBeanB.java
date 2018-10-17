package com.example.spring.beans;

public class ConstructorReferenceBeanB {

    ConstructorReferenceBeanA constructorReferenceBeanA;

    public ConstructorReferenceBeanB(ConstructorReferenceBeanA constructorReferenceBeanA) {
        this.constructorReferenceBeanA = constructorReferenceBeanA;
        System.out.println("ConstructorReferenceBeanB created...");
    }
}
