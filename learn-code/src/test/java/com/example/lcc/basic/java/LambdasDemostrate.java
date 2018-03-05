package com.example.lcc.basic.java;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Created by liucongcong on 2018/2/9.
 */
public class LambdasDemostrate {
    @Test
    public void testLambdas(){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("a","b");
        unaryOperator(String::toLowerCase,"AbC");
        binaryOperator(Integer::sum,1,3);
        try {
            Stream<String> lines = Files.lines(null);
        } catch (IOException e) {
        }
    }

    private void unaryOperator(UnaryOperator<String> operator,String parameter){
        System.out.println(operator.apply(parameter));
    }
    private void binaryOperator(BinaryOperator<Integer> operator, Integer a,Integer b){
        System.out.println(operator.apply(a,b));
    }

    @FunctionalInterface
    static interface MyRunnable{
        void run();
    }
}
