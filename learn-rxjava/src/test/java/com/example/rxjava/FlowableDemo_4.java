package com.example.rxjava;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * from
 * https://www.jianshu.com/p/12638513424f
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

        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                        System.out.println("发射------> 1");
                        emitter.onNext(1);
                        System.out.println("发射------> 2");
                        emitter.onNext(2);
                        System.out.println("发射------> 3");
                        emitter.onNext(3);
                        System.out.println("发射完成！");
                        emitter.onComplete();

                    }
                }, BackpressureStrategy.BUFFER) // create方法多了个BackpressureStrategy类型的参数
                .subscribeOn(Schedulers.newThread())   //设置发布线程调度器
                .observeOn(Schedulers.newThread())     //设置观察者线程调度器
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) { //这里onSubscribe接收的参数不是Disposable而是subscription
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("接收完成！");
                    }
                });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        /**
         * 通过运行发现与 Flowable 与 Observable运行结果上其实没什么区别
         * 主要的区别
         * 一、create方法中多了一个BackpressureStrategy类型的参数。
         * 二、onSubscribe回调的参数不是Disposable而是Subscription，多了行代码：
         * subscription.request(Long.MAX_VALUE);
         * 三、Flowable发射数据时，使用的发射器是FlowableEmitter而不是ObservableEmitter
         *
         * BackpressureStrategy背压策略
         * ERROR
         * 在此策略下，如果放入Flowable的异步缓存池中的数据超限了，则会抛出MissingBackpressureException异常。
         * DROP
         * 在此策略下，如果Flowable的异步缓存池满了，会丢掉将要放入缓存池中的数据。
         * LATEST
         * 与Drop策略一样，如果缓存池满了，会丢掉将要放入缓存池中的数据，不同的是，
         * 不管缓存池的状态如何，LATEST都会将最后一条数据强行放入缓存池中。
         * BUFFER
         * 此策略下，Flowable的异步缓存池同Observable的一样，没有固定大小，可以无限制向里添加数据，
         * 不会抛出MissingBackpressureException异常，但会导致OOM。
         * MISSING
         * 此策略表示，通过Create方法创建的Flowable没有指定背压策略，不会对通过OnNext发射的数据做缓存或丢弃处理，
         * 需要下游通过背压操作符（onBackpressureBuffer()/onBackpressureDrop()/onBackpressureLatest()）指定背压策略。
         *
         * onBackpressureXXX背压操作符
         * Flowable除了通过create创建的时候指定背压策略，也可以在通过其它创建操作符just，fromArray等创建后通过背压操作符
         * 指定背压策略。
         * onBackpressureBuffer()对应BackpressureStrategy.BUFFER
         * onBackpressureDrop()对应BackpressureStrategy.DROP
         * onBackpressureLatest()对应BackpressureStrategy.LATEST
         *
         *
         * Subscription
         * Subscription与Disposable均是观察者与可观察对象建立订阅状态后回调回来的参数，
         * 如同通过Disposable的dispose()方法可以取消Observer与Oberverable的订阅关系一样，
         * 通过Subscription的cancel()方法也可以取消Subscriber与Flowable的订阅关系。
         * 不同的是接口Subscription中多了一个方法request(long n)，如上面代码中的：
         *  s.request(Long.MAX_VALUE);
         *  如果去掉这个会发现Subscriber不再接收数据
         *  这是因为Flowable在设计的时候，采用了一种新的思路——响应式拉取方式，来设置下游对数据的请求数量，
         *  上游可以根据下游的需求量，按需发送数据。
         *  如果不显示调用request则默认下游的需求量为零，所以运行上面的代码后，上游Flowable发射的数据不会交给
         *  下游Subscriber处理。
         *  多次调用会累加
         *  s.request(3);
         *  s.request(4);
         *  结果会获取7条
         *
         *
         */


    }

    @Test
    public void backpressureERROR() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                        /**
                         * 这里设置为129 会产生异常是因为
                         * Flowable的默认缓冲区大小为128 而改为128 或者加载buffer 这段代码则不会产生MissingBackpressureException
                         */
                        for (int i = 1; i <= 129; i++) {
                            emitter.onNext(i);
                        }
                        emitter.onComplete();

                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                        }
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete!");
                    }
                });


        try {
            Thread.sleep(110000);
        } catch (InterruptedException e) {
        }

    }

    @Test
    public void backpressureDROP() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                        /**
                         * 这里设置为129 因为缓冲区大小为128 因此 只能接受到1-128 而 129则会被抛弃
                         */
                        for (int i = 1; i <= 150; i++) {
                            emitter.onNext(i);
                        }
                        emitter.onComplete();

                    }
                }, BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            if (integer.equals(1)) {
                                Thread.sleep(1000);
                            }
                        } catch (InterruptedException e) {
                        }
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete!");
                    }
                });


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

    }

    @Test
    public void backpressureLATEST() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                        /**
                         * 这里设置为129 因为缓冲区大小为128 因此 只能接受到1-128 而 129则会被抛弃
                         * 130会被强制接收
                         */
                        for (int i = 1; i <= 130; i++) {
                            emitter.onNext(i);
                        }
                        emitter.onComplete();

                    }
                }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            if (integer.equals(1)) {
                                Thread.sleep(1000);
                            }
                        } catch (InterruptedException e) {
                        }
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete!");
                    }
                });


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }


    }

    @Test
    public void backpressureOnXXX() {
        Flowable.range(1, 130)
                .onBackpressureDrop(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("droped:" + integer);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        if (integer.equals(1)) {
                            Thread.sleep(1000);
                        }
                        System.out.println("recieved:" + integer);
                    }
                });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void subscriberRequest() {
        Flowable.range(1, 135)
                .onBackpressureDrop(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("droped:" + integer);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        //观察者请求几条 就会收到几条，多余的不会不发送 而是放到了缓存区
                        subscription.request(3);
                        subscription.request(4);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("接收：" + integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void emitterRequest() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                        /**
                         * 如果使用调度器 上下游不在一个线程处理的话 emitter.requested()是根据缓冲区大小来决定的
                         * 如果在一个线程，那么就是下游的request的数量
                         */
                        for (int i = 1; i <= 130; i++) {
                            System.out.printf("发射:%s,剩余请求:%s.%n", i, emitter.requested());
                            emitter.onNext(i);
                        }
                        emitter.onComplete();

                    }
                }, BackpressureStrategy.DROP)
                //.subscribeOn(Schedulers.newThread())
                //.observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(4);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            if (integer.equals(1)) {
                                Thread.sleep(1000);
                            }
                        } catch (InterruptedException e) {
                        }
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete!");
                    }
                });


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void demoRequest() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                        int i = 0;
                        while (true) {
                            if (emitter.requested() == 0) {
                                continue;
                            }
                            System.out.println("待发送请求数量：" + emitter.requested() + "，发射：" + i);
                            emitter.onNext(i);
                            i++;
                        }
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(1);
                        this.subscription = subscription;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                        }
                        System.out.println("接收 ---> " + integer);
                        subscription.request(1); //每收到一条增加一个请求量
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void showOOM() {
        /**
         * 由于上下游分别在各自的线程中独立处理数据（如果上下游在同一线程中，下游对数据的处理会堵塞上游数据的发送
         * ，上游发送一条数据后会等下游处理完之后再发送下一条）
         * ，而上游发送数据速度远大于下游接收数据的速度，造成上下游流速不均，导致数据累积，最后引起内存溢出。
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
