package com.example.lcc.basic.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import org.junit.Test;

import java.util.Arrays;

/**
 * from
 * https://www.jianshu.com/p/12638513424f
 */
public class OperatorsDemo_2 {

    @Test
    public void operators() {
        /**
         * Operators操作符
         * 其实质是函数式编程中的高阶函数，是对响应式编程的各个过程拆分封装后的产物。以便于我们操作数据流。
         * 按照其作用具体可分为以下几类：
         * 创建：创建一个可观察对象Observable并发射数据
         * 过滤：从Observable发射的数据中取出特定的值
         * 变换：对Observable发射的数据执行变换操作
         * 组合：组合多个Observable,例如：{1,2,3}+{4,5,6}-->{1,2,3,4,5,6}
         * 聚合：聚合多个Observable，例如：{1,2,3}+{4,5,6}-->{[1,4],[2,5],[3,6]}
         */

        /**
         * 创建操作：
         * create:基础创建操作符
         * just:创建一个Observable，可接受一个或多个参数，将每个参数逐一发送
         * fromArray:创建一个Observable，接受一个数组，并将数组中的数据逐一发送
         * fromIterable：</b>创建一个Observable，接受一个可迭代对象，并将可迭代对象中的数据逐一发送
         * range：创建一个Observable，发送一个范围内的整数序列
         */
        //发送从1到15
        Observable.range(1, 15)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("连续整数1-15：" + integer);
                    }
                });
        /**
         * 过滤操作
         * filter：filter使用Predicate条件函数，来判断Observable发射的每一个值是否满足这个条件
         * ，如果满足，则继续向下传递，如果不满足，则过滤掉。
         * 这点跟java8中的stream filter 一样
         */
        Observable.range(1, 15).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                //只输出偶数序列
                return integer % 2 == 0;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("1-15间的偶数：" + integer);
            }
        });

        /**
         * distinct：去重操作,即只发送未发送过的数据
         * 可以指定重复判定函数
         */
        Observable.fromArray("ss1", "ss2", "ss1", "ss2")
                .distinct()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("去重：" + s);
                    }
                });

        /**
         * 函数叠加
         * 所有操作符均可叠加使用
         */
        Observable.fromArray(1, 1, 3, 3, 3, 5, 4, 4, 10, 10, 9)
                .distinct()
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("去重+取偶：" + integer);
                    }
                });

        /**
         * 变换操作（map 与 flatMap）
         * map:对Observable发射的每一项数据应用一个函数，执行变换操作
         * 需要传入一个变换函数来执行变换操作
         *
         */

        Observable.fromArray(1, 1, 3, 3, 3, 5, 4, 4, 10, 10, 9)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        return integer + " x " + 100 + "=" + String.valueOf(integer * 100);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("变换：" + s);
                    }
                });

        /**
         * flatMap：
         * 先将 原始Observable映射成多个Observable  然后将多个Observable合并成一个进行发送数据
         *
         * 如下面的例子，如果使用map 则无法达到目的说 将数组映射为一个元素进行发射
         * 而使用flatMap 进行一对多变换 则可达目的
         */
        String[] s1 = {"aa", "bb"};
        String[] s2 = {"aa", "bb"};
        String[] s3 = {"aa", "bb"};

        Observable.just(s1, s2, s3)
                .flatMap(new Function<String[], ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String[] ss) throws Exception {
                        return Observable.fromArray(ss);
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        /**
         * 组合操作（mergeWith与concatWith）
         * mergeWith：合并多个Observable发射的数据，可能会让Observable发射的数据交错。
         */
        Observable.fromArray(1, 3, 5, 7)
                .mergeWith(Observable.fromArray(2, 3, 4, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("MergeWith：" + integer);
                    }
                });
        /**
         * concatWith：通mergeWith一样是合并Observable，不过concat不会使数据交错
         */
        Observable.fromArray(1, 3, 5, 7)
                .concatWith(Observable.fromArray(2, 3, 4, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("concatWith：" + integer);
                    }
                });


        /**
         * 聚合操作（zipWith）：
         * zipWith：将多个Obversable发射的数据，
         * 通过一个函数BiFunction对对应位置的数据处理后放到一个新的Observable中发射，
         * 所发射的数据个数与最少的Observabel中的一样多。
         */
        Observable.fromArray(1, 1, 2, 2)
                .zipWith(Observable.fromArray(3, 4, 5), new BiFunction<Integer, Integer, Integer[]>() {
                    @Override
                    public Integer[] apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return new Integer[]{integer, integer2};
                    }
                })
                .subscribe(new Consumer<Integer[]>() {
                    @Override
                    public void accept(Integer[] integers) throws Exception {
                        System.out.println("zipWith:" + Arrays.toString(integers));
                    }
                });

    }

}
