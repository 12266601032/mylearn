package com.example.lcc.basic.java.stream;

import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import static java.util.function.BinaryOperator.maxBy;
import static java.util.stream.Collectors.*;

/**
 * Created by liucongcong on 2018/3/6.
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
