package com.example.rxjava;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * from
 * https://www.jianshu.com/p/12638513424f
 */
public class SimpleDemo_1 {
    public static void main(String[] args) {

        /**
         * Observable
         * 抽象类Observable是接口ObservableSource下的一个抽象实现，我们可以通过
         * Observable创建一个可观察对象发射数据流
         *
         * 关于发射器
         * public interface Emitter<T> {
         *      //用来发送数据，可多次调用，每调用一次发送一条数据
         *      void onNext(@NonNull T value);
         *      //用来发送异常通知，只发送一次，若多次调用只发送第一条
         *      void onError(@NonNull Throwable error);
         *      //用来发送完成通知，只发送一次，若多次调用只发送第一条
         *      void onComplete();
         * }
         *
         * 其中onError与onComplete互斥
         * 如果onNext抛出异常会调用onError结束
         * Emitter调用onNext发送数据时，Observer会通过onNext接收数据。
         * Emitter调用onError发送异常通知时，Observer会通过onError接收异常通知。
         * Emitter调用onComplete发送完成通知时，Observer会通过onComplete接收完成通知。
         *
         */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                //发送 hello word
                emitter.onNext("Hello word.");
                //标记完成
                emitter.onComplete();
            }
        });
        /**
         * Observer
         * 创建观察者
         */
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //在observer 与 observable之间建立订阅关系时触发
                System.out.println("onSubscribe：" + d.isDisposed());
                //其中Disposable 对象 是用来 解除订阅关系的
            }

            @Override
            public void onNext(@NonNull String s) {
                //消息
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                //产生异常时触发
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                //正常结束
                System.out.println("completed!");
            }
        };

        /**
         * Observable与observer直接建立订阅关系
         */
        observable.subscribe(observer);


        //去掉中间变量
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello word!");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe：" + d.isDisposed());
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
                System.out.println("completed!");
            }
        });

        /**
         * 使用函数式  如果配合lambda 将会更加的简洁
         * just 是专门用来发射单条数据的
         * Consumer在这里的作用是 Observer的四个函数中的onNext的函数式对象，做的是onNext做的事
         */
        Observable.just("Hello word!")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
        Observable.just("Hello word!")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //onNext 函数式对象
                        System.out.println(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //onError 函数式对象

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //onComplete 函数式对象

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //onSubscribe 函数式对象
                        System.out.println("onSubscribe:" + disposable.isDisposed());
                    }
                });


    }

    @Test
    public void listDemo() {
        listFun(Arrays.asList("sss1", "sss12", "sss13", "sss14"));
    }

    @Test
    public void listDemo2() {
        //简化版
        Observable.fromArray("sss1", "sss12", "sss13", "sss14")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    public void listFun(List<String> list) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                try {
                    for (String s : list) {
                        emitter.onNext(s);
                    }
                } catch (Exception e) {
                    emitter.onError(e);
                }
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println(s);
                //throw new RuntimeException();
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

    }
}
