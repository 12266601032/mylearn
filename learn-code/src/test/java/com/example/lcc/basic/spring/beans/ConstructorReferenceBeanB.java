package com.example.lcc.basic.spring.beans;

public class ConstructorReferenceBeanB {

    ConstructorReferenceBeanA constructorReferenceBeanA;

    public ConstructorReferenceBeanB(ConstructorReferenceBeanA constructorReferenceBeanA) {
        this.constructorReferenceBeanA = constructorReferenceBeanA;
        System.out.println("ConstructorReferenceBeanB created...");
    }
}
