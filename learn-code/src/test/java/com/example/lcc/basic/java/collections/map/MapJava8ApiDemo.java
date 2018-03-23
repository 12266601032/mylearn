package com.example.lcc.basic.java.collections.map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 */
public class MapJava8ApiDemo {

    Map<String, List<Block>> map = Maps.newHashMap();
    List<Block> list = Lists.newArrayList();

    @Data
    private class Block {
        private String shap;
        private String name;

        public Block(String shap, String name) {
            this.shap = shap;
            this.name = name;
        }
    }

    @Before
    public void init() {
        list.clear();
        map.clear();
        list.add(new Block("长方体", "g1"));
        list.add(new Block("圆柱", "g2"));
        list.add(new Block("圆柱", "g3"));
        list.add(new Block("长方体", "g4"));
        list.add(new Block("圆柱", "g5"));
        list.add(new Block("梯形", "g6"));
        list.add(new Block("梯形", "g7"));
    }

    /**
     * 对一堆积木进行分类处理，
     * 根据积木形状，最终形成一个Map<String, List<Block>>
     * key为形状 value为这种形状的所有积木
     */
    @Test
    public void testClassify() {


        /*使用遍历的方式*/
        for (Block block : list) {
            List<Block> bs = map.get(block.getShap());
            if (bs == null) {
                bs = Lists.newArrayList();
                map.put(block.getShap(), bs);
            }
            bs.add(block);
        }
        System.out.println(map);
        init();

        //使用java8的Stream 更简洁的完成
        Map<String, List<Block>> collect = list.stream().collect(groupingBy(Block::getShap));
        System.out.println(collect);
    }


    @Test
    public void compute() {
        /*
         * 使用compute:
         * 首先从map中get出来对应的旧值，然后根据指定的remapping函数进行获取新值替换
         * 如果新值为null 旧值不为null 则直接remove掉元素
         */
        map.compute("a1", (key, value) -> {
            if (value == null) {
                value = Lists.newArrayList();
            }
            value.addAll(list);
            return value;
        });
        System.out.println("compute-->" + map);
        init();
        /*如果key 对应的值为null 就将function中返回的值put进去*/
        map.computeIfAbsent("a3", (key) -> {
            return list;
        });
        System.out.println("computeIfAbsent-->" + map);
        init();
        /*与compute方式类似，不过是只有对应key的value 不为null才执行BiFunction 去替换新值*/
        map.computeIfPresent("a4", (key, value) -> {
            return list;
        });
        System.out.println("computeIfPresent-->" + map);
    }
}
