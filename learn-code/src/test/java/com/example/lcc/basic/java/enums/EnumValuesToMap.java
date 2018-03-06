package com.example.lcc.basic.java.enums;

import org.junit.Test;

import java.util.Map;
import java.util.function.DoubleBinaryOperator;

import static java.util.stream.Collectors.*;

import java.util.stream.Stream;

public class EnumValuesToMap {

    @Test
    public void testValuesToMap() {
        Operation plus = Operation.enumsMap.get("+");
        System.out.println(plus.apply(1,2));
    }

    private enum Operation {
        PLUS("+", (x, y) -> x + y),
        MINUS("-", (x, y) -> x - y),
        TIMES("*", (x, y) -> x * y),
        DIVIDE("/", (x, y) -> x / y);
        private final String symbol;
        private final DoubleBinaryOperator op;


        /**
         * 将所有的枚举值转换为一个 key 为toString value为枚举值的 Map
         * 如果toString方法返回有冲突的key会报IllegalStateException
         */
        public static Map<String, Operation> enumsMap = Stream.of(values()).collect(
                toMap(Object::toString, e -> e));

        Operation(String symbol, DoubleBinaryOperator op) {
            this.symbol = symbol;
            this.op = op;


        }

        @Override
        public String toString() {
//            return "+";
            return symbol;
        }

        public double apply(double x, double y) {
            return op.applyAsDouble(x, y);
        }
    }
}
