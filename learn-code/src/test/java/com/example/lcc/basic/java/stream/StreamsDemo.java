package com.example.lcc.basic.java.stream;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * Created by liucongcong on 2018/3/6.
 */
public class StreamsDemo {

    @Test
    public void sumMap() {
        Map<String, BigDecimal> map = Maps.newHashMap();
        map.put("a", BigDecimal.ONE);
        map.put("b", BigDecimal.TEN);
        map.put("c", BigDecimal.ONE);
        map.put("d", BigDecimal.ZERO);
        map.put("e", BigDecimal.ONE);
        map.put("f", BigDecimal.ONE);

        Optional<Integer> reduce = map.values().stream() //将map转换为stream
                .map(BigDecimal::intValue) //重映射成一个Int类型的Stream
                .reduce(Integer::sum);  //提供BinaryOperator函数 做求和
        System.out.printf("result : %d %n", reduce.get());

    }
}
