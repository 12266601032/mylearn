package com.example.lcc.basic.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

/**
 * Created by liucongcong on 2018/3/9.
 */
public class FlowableDemo_4 {

    @Test
    public void flowable() {
        /**
         * 创建和使用观察者Observer与Observable
         * 1、创建一个可观察对象Observable发射数据流
         * 2、通过操作符Operator加工处理数据流
         * 3、通过线程调度器Scheduler指定操作数据流所在的线程
         * 4、创建一个观察者Observer接收响应数据流
         * 背压（backpressure）
         * 数据流发射，处理，响应可能在各自的线程中独立进行，上游在发射数据的时候，
         * 不知道下游是否处理完，也不会等下游处理完之后再发射。
         * 这样，如果上游发射的很快而下游处理的很慢，会怎样呢？
         * 将会产生很多下游没来得及处理的数据，这些数据既不会丢失，也不会被垃圾回收机制回收，
         * 而是存放在一个异步缓存池中，如果缓存池中的数据一直得不到处理，越积越多，最后就会造成内存溢出，
         * 这便是Rxjava中的背压问题
         *
         * Flowable
         *
         * Flowable是为了解决背压（backpressure）问题，而在Observable的基础上优化后的产物，与
         * Observable不是同一组观察者模式下的成员，Flowable是Publisher与Subscriber这一组观察者模式中Publisher的典型实现，
         * Observable是ObservableSource/Observer这一组观察者模式中ObservableSource的典型实现；
         * 所以在使用Flowable的时候，可观察对象不再是Observable,而是Flowable;观察者不再是Observer，
         * 而是Subscriber。Flowable与Subscriber之间依然通过subscribe()进行关联。
         * 有些朋友可能会想，既然Flowable是在Observable的基础上优化后的产物，Observable能解决的问题Flowable都能进行解决，
         * 何不抛弃Observable而只用Flowable呢。其实，这是万万不可的，他们各有自己的优势和不足。
         * 由于基于Flowable发射的数据流，以及对数据加工处理的各操作符都添加了背压支持，附加了额外的逻辑，
         * 其运行效率要比Observable低得多。
         * 因为只有上下游运行在各自的线程中，且上游发射数据速度大于下游接收处理数据的速度时，才会产生背压问题。
         * 所以，如果能够确定上下游在同一个线程中工作，或者上下游工作在不同的线程中，
         * 而下游处理数据的速度高于上游发射数据的速度，则不会产生背压问题，就没有必要使用Flowable，以免影响性能。
         * 通过Flowable发射处理数据流的基础代码如下：

         */
    }

    @Test
    public void showOOM() {
        /**
         * 由于上下游分别在各自的线程中独立处理数据（如果上下游在同一线程中，下游对数据的处理会堵塞上游数据的发送
         * ，上游发送一条数据后会等下游处理完之后再发送下一条）
         * ，而上游发送数据速度远大于下游接收数据的速度，造成上下游流速不均，导致数据累计，最后引起内存溢出。
         */
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        int i = 0;
                        while (true) {
                            emitter.onNext(String.valueOf(i));
                        }
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Thread.sleep(5000);
                        System.out.println(s);
                    }
                });

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
        }
    }

}
