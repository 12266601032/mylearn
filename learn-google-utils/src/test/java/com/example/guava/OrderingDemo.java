package com.example.guava;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.List;

/**
 * Ordering 拥有3大类方法
 *
 *  1、创建排序器：
 *  natural()           对可排序类型做自然排序，如数字按大小，日期按先后排序
 *  usingToString()     按对象的字符串形式做字典排序[lexicographical ordering]
 *  from(Comparator)    把给定的Comparator转化为排序器
 *
 *  2、链式调用方法：通过链式调用，可以由给定的排序器衍生出其它排序器
 *  reverse()               获取语义相反的排序器
 *  nullsFirst()            使用当前排序器，但额外把null值排到最前面。
 *  nullsLast()             使用当前排序器，但额外把null值排到最后面。
 *  compound(Comparator)    合成另一个比较器，以处理当前排序器中的相等情况。
 *  lexicographical()       基于处理类型T的排序器，返回该类型的可迭代对象Iterable<T>的排序器。
 *  onResultOf(Function)    对集合中元素调用Function，再按返回值用当前排序器排序
 *
 *  3、运用排序器：Guava的排序器实现有若干操纵集合或元素值的方法
 *  greatestOf(Iterable iterable, int k)        获取可迭代对象中最大的k个元素。
 *  isOrdered(Iterable)                         判断可迭代对象是否已按排序器排序：允许有排序值相等的元素。
 *  sortedCopy(Iterable)                        获取排序结果
 *  min(E, E)                                   返回两个参数中最小的那个。如果相等，则返回第一个参数。
 *  min(E, E, E, E...)                          返回多个参数中最小的那个。如果有超过一个参数都最小，则返回第一个最小的参数
 *  min(Iterable)                               返回迭代器中最小的元素。如果可迭代对象中没有元素，则抛出NoSuchElementException。
 *
 */
public class OrderingDemo {

    @Test
    public void ordering(){
        Ordering<Foo> ordering = Ordering.natural() //自然排序 对可排序类型做自然排序，如数字按大小，日期按先后排序
                .nullsFirst()       // null放前面
                .onResultOf(new Function<Foo, String>() {
                    @Override
                    public String apply(Foo input) {
                        return input.sortedBy;
                    }
                }); //不为null的用这个进行比较
        List<Foo> fs = Lists.newArrayList();
        Foo f = new Foo();
        f.sortedBy = "a";
        fs.add(f);
        Foo f1 = new Foo();
        f1.sortedBy = "c";
        fs.add(f1);
        Foo f2 = new Foo();
        f2.sortedBy = null;
        fs.add(f2);
        List<Foo> foos = ordering.sortedCopy(fs);


        System.out.println(foos);
        List<Foo> foos1 = ordering.reverse() //获取语义相反的排序器
                .sortedCopy(fs);
        System.out.println(foos1);
    }

    class Foo {
        String sortedBy;
        int notSortedBy;

        @Override
        public String toString() {
            return "Foo{" +
                    "sortedBy='" + sortedBy + '\'' +
                    ", notSortedBy=" + notSortedBy +
                    '}';
        }
    }
}
