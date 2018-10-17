package com.example.java.stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import static java.util.function.BinaryOperator.maxBy;
import static java.util.stream.Collectors.*;

/**
 */
public class StreamsDemo {

    @Before
    public void init() {
        map.put("a", BigDecimal.ONE);
        map.put("b", BigDecimal.TEN);
        map.put("c", BigDecimal.ONE);
        map.put("d", BigDecimal.ZERO);
        map.put("e", BigDecimal.ONE);
        map.put("f", BigDecimal.ONE);

        albumStream = Stream.of(
                new Album(new Artist("a1"), 1)
                , new Album(new Artist("a1"), 10)
                , new Album(new Artist("a2"), 22)
                , new Album(new Artist("a2"), 25)
                , new Album(new Artist("a3"), 99)
                , new Album(new Artist("a3"), 131)
                , new Album(new Artist("a3"), 80)
        );

        words.add("foo");
        words.add("oof");
        words.add("fio");
        words.add("foi");
        words.add("fff");
        words.add("foe");
        words.add("feo");
    }

    private Map<String, BigDecimal> map = Maps.newHashMap();

    @Test
    public void sumMap() {
        Optional<Integer> reduce = map.values().stream() //将map转换为stream
                .map(BigDecimal::intValue) //重映射成一个Int类型的Stream
                .reduce(Integer::sum);  //提供BinaryOperator函数 做求和
        System.out.printf("result : %d %n", reduce.get());
    }

    private Stream<Album> albumStream;

    @Test
    public void collector() {
        /*Map<Artist, Album> artistAlbumMap = albumStream.collect(
                Collectors.toMap(Album::artist, value -> value,
                        BinaryOperator.maxBy(Comparator.comparing(Album::sales))));*/
        /*
         * 将这个流转换为一个key为艺术家value为每个艺术家最畅销的专辑的map
         *
         * 通过Collectors.toMap(Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper,
                                    BinaryOperator<U> mergeFunction)
         * 构造Collector
         * 每次遇到key已存在元素在map中就使用mergeFunction 来合并元素
         * 最终利用mergeFunction 来实现每次替换销量更大的专辑完成目的。
         */
        Map<Artist, Album> artistAlbumMap = albumStream.collect(
                toMap(Album::artist, value -> value,
                        maxBy(comparing(Album::sales))));
        System.out.println(artistAlbumMap);

    }


    private List<String> words = Lists.newArrayList();

    @Test
    public void groupingby() {
        /**
         * 1、根据使用同字符的单词进行分组
         * word -> alphabetize(word) 是一个分类函数，用来获取单词不分大小写的正序排列
         *
         * 2、如果希望指定map中value的容器 可以指定一个下游的collector 比较简单的就是toSet()也可以直接toCollection然后提供一个
         * 工厂函数。
         *
         * 3、下游collector给定一个counting() 可以返回一个key分分类 value为分类元素个数的map
         *
         * 4、指定一个map的工厂函数可以设定Map的实现类
         *
         */
        Map<String, List<String>> collect1 = words.stream()
                .collect(groupingBy(word -> alphabetize(word)));
        Map<String, LinkedList<String>> collect2 = words.stream()
                .collect(groupingBy(word -> alphabetize(word), toCollection(LinkedList::new)));
        Map<String, Long> collect3 = words.stream()
                .collect(groupingBy(word -> alphabetize(word), counting()));
        TreeMap<String, Set<String>> collect4 = words.stream()
                .collect(groupingBy(word -> alphabetize(word), TreeMap::new, toSet()));

        System.out.println(collect1);
        System.out.println(collect2);
        System.out.println(collect3);
        System.out.println(collect4);
    }

    @Test
    public void testSteamIterator() throws Exception {
        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        /*for (Integer i : (Iterable<? extends Integer>) integerStream::iterator) {
            System.out.println(i);
        }*/
        //stream::iterator 实际是一个Iterable的函数式对象
        // ，这样可以直接参与foreach迭代，而不需要进行强转
        //iterableOf属于一个适配方法
        for (Integer i :
                iterableOf(integerStream)) {
            System.out.println(i);
        }
        try(Stream<String> s = Files.lines(Paths.get("test.properties"))){

        }
    }

    // Adapter from Stream<E> to Iterable<E>
    public static <E> Iterable<E> iterableOf(Stream<E> stream) {
        return stream::iterator;
    }

    private String alphabetize(String word) {
        char[] chars = word.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    //艺术家
    @ToString
    @EqualsAndHashCode
    private class Artist {
        private String name;

        public Artist(String name) {
            this.name = name;
        }
    }

    //专辑
    @ToString
    @EqualsAndHashCode
    private class Album {
        private Artist artist;
        private Integer sales;

        public Album(Artist artist, Integer sales) {
            this.artist = artist;
            this.sales = sales;
        }

        public Artist artist() {
            return this.artist;
        }

        public Integer sales() {
            return this.sales;
        }
    }
}
