package com.example.lcc.basic.guava;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import org.junit.Test;

/**
 * 区间
 * <table>
 * <tr><td><b>Notation</b> <td><b>Definition</b>        <td><b>Factory method</b>
 * <tr><td>{@code (a..b)}  <td>{@code {x | a < x < b}}  <td>{@link Range#open open}
 * <tr><td>{@code [a..b]}  <td>{@code {x | a <= x <= b}}<td>{@link Range#closed closed}
 * <tr><td>{@code (a..b]}  <td>{@code {x | a < x <= b}} <td>{@link Range#openClosed openClosed}
 * <tr><td>{@code [a..b)}  <td>{@code {x | a <= x < b}} <td>{@link Range#closedOpen closedOpen}
 * <tr><td>{@code (a..+∞)} <td>{@code {x | x > a}}      <td>{@link Range#greaterThan greaterThan}
 * <tr><td>{@code [a..+∞)} <td>{@code {x | x >= a}}     <td>{@link Range#atLeast atLeast}
 * <tr><td>{@code (-∞..b)} <td>{@code {x | x < b}}      <td>{@link Range#lessThan lessThan}
 * <tr><td>{@code (-∞..b]} <td>{@code {x | x <= b}}     <td>{@link Range#atMost atMost}
 * <tr><td>{@code (-∞..+∞)}<td>{@code {x}}              <td>{@link Range#all all}
 * </table></blockquote>
 */
public class RangeDemo {

    @Test
    public void range(){
        /*(-∞..b)*/
        Range<Integer> range = Range.lessThan(1);
        /*[a..b]*/
        Range<Integer> closed = Range.closed(1, 3);
        //区间运算contains
        System.out.printf("contains:%b.%n",closed.contains(2));
        //区间运算containsAll
        System.out.printf("containsAll:%b.%n",closed.containsAll(Ints.asList(1,2,3)));
        //判断是否为空区间
        Range.closedOpen(4, 4).isEmpty(); // returns true
        Range.openClosed(4, 4).isEmpty(); // returns true
        Range.closed(4, 4).isEmpty(); // returns false
        Range.open(4, 4).isEmpty(); // Range.open throws IllegalArgumentException
        //区间端点
        Range.closed(3, 10).lowerEndpoint(); // returns 3
        Range.open(3, 10).lowerEndpoint(); // returns 3
        //BoundType.CLOSED 获取区间边界类型（开、闭）
        Range.closed(3, 10).lowerBoundType(); // returns CLOSED
        //BoundType.OPEN
        Range.open(3, 10).upperBoundType(); // returns OPEN

        //区间是否相连
        Range.closed(3, 5).isConnected(Range.open(5, 10)); // returns true
        Range.closed(0, 9).isConnected(Range.closed(3, 4)); // returns true
        Range.closed(0, 5).isConnected(Range.closed(3, 9)); // returns true
        Range.open(3, 5).isConnected(Range.open(5, 10)); // returns false
        Range.closed(1, 5).isConnected(Range.closed(6, 10)); // returns false
        //既包含于第一个区间，又包含于另一个区间的最大区间 当且仅当两个区间是相连的，它们才有交集
        //如果两个区间没有交集，该方法将抛出IllegalArgumentException。
        Range.closed(3, 5).intersection(Range.open(5, 10)); // returns (5, 5]
        Range.closed(0, 9).intersection(Range.closed(3, 4)); // returns [3, 4]
        Range.closed(0, 5).intersection(Range.closed(3, 9)); // returns [3, 5]
        Range.open(3, 5).intersection(Range.open(5, 10)); // throws IAE
        Range.closed(1, 5).intersection(Range.closed(6, 10)); // throws IAE

        //“同时包括两个区间的最小区间” 如果是相连区间那就是区间的并集
        Range.closed(3, 5).span(Range.open(5, 10)); // returns [3, 10)
        Range.closed(0, 9).span(Range.closed(3, 4)); // returns [0, 9]
        Range.closed(0, 5).span(Range.closed(3, 9)); // returns [0, 9]
        Range.open(3, 5).span(Range.open(5, 10)); // returns (3, 10)
        Range.closed(1, 5).span(Range.closed(6, 10)); // returns [1, 10]





    }


}
