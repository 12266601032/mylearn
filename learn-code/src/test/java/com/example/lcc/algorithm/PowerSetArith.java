package com.example.lcc.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PowerSetArith {

    public List<List<String>> powerSet(List<String> set) {
        //已知所求集合的幂集会有2^n个元素
        int size = 2 << set.size();
        List<List<String>> powerSet = new ArrayList<>(size);
        //首先空集肯定是集合的幂集
        powerSet.add(Collections.emptyList());
        for (String element : set) {
            //计算当前元素与已存在幂集的组合
            int preSize = powerSet.size();
            for (int i = 0; i < preSize; i++) {
                List<String> combineSubset = new ArrayList<>(powerSet.get(i));
                combineSubset.add(element);
                powerSet.add(combineSubset);
            }
        }
        return powerSet;
    }


    @Test
    public void testPowerSet() {
        List<String> set = Arrays.asList("a", "b", "c", "d");
        List<List<String>> powerSet = powerSet(set);
        System.out.println(Arrays.toString(powerSet.toArray()));
    }


    public List<List<String>> bitPowerSet(List<String> set) {

        //去2^n - 1 的数字作为对应的位图数值，其中n为元素个数
        int bitmapMaxNum = 2 << (set.size() - 1);
        List<List<String>> powerSet = new ArrayList<>(2 << set.size());
        powerSet.add(Collections.emptyList()); //添加空集
        for (int i = 1; i < bitmapMaxNum; i++) { //0表示空集，因此直接从1开始即可
            powerSet.add(subset(set, i));
        }
        return powerSet;
    }

    //根据数值的bitmap获取对应的子集
    public List<String> subset(List<String> set, int bitMapNum) {
        List<String> subset = new ArrayList<>();
        for (int i = 0; i < set.size(); i++) {
            if (((bitMapNum >> i) & 1) == 1) { // 判断对位元素是否存在
                subset.add(set.get(i));
            }
        }
        return subset;
    }

    @Test
    public void testBitPowerSet() {
        List<String> set = Arrays.asList("a", "b", "c", "d");
        List<List<String>> powerSet = bitPowerSet(set);
        System.out.println(Arrays.toString(powerSet.toArray()));
    }
}
