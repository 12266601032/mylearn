package com.example.lcc.basic.rxjava;

import ch.qos.logback.core.helpers.ThrowableToStringArray;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.junit.runner.notification.RunListener;

import java.util.concurrent.Executor;

/**
 * from
 * https://www.jianshu.com/p/12638513424f
 */
public class SchedulerDemo_3 {

    @Test
    public void scheduler() throws Exception {
        /**
         * Scheduler 是RxJava对异步操作的支持
         * 前面的代码中Observable 发射数据流Observer接收数据流
         * 以及Operators加工数据流都发生在同一个线程中，实现的是一个同步的函数响应式
         * 但是函数响应式的实际应用却不是这样的，大部分都是后台处理，前台响应的一个过程。Observable生成发射数据流，
         * 以及Operators加工数据流都是在后台线程中进行，而Observer在前台线程中接受并相应数据。
         * Scheduler(线程调度器)可以让RxJava的线程切换变得简单明了，即使程序逻辑变得十分复杂，他依然能够保持简单明了。
         *
         * subscribeOn
         * Observable<T> subscribeOn(Scheduler scheduler)
         * subscribeOn通过接收一个Scheduler参数，来指定对数据的处理运行在特定的线程调度器Scheduler上。
         * 若多次设定，则只有一次起作用（第一次）。
         *
         * observeOn
         * Observable<T> observeOn(Scheduler scheduler)
         * observeOn同样接收一个Scheduler参数，来指定下游操作运行在特定的线程调度器Scheduler上。
         * 若多次设定，每次均起作用。
         *
         *
         * Scheduler种类
         * Schedulers.io()：
         * 用于IO密集型的操作，例如读写SD卡文件，查询数据库，访问网络等，具有线程缓存机制，在此调度器接收到任务后，
         * 先检查线程缓存池中，是否有空闲的线程，如果有，则复用，如果没有则创建新的线程，并加入到线程池中，
         * 如果每次都没有空闲线程使用，可以无上限的创建新线程。
         * Schedulers.newThread()：
         * 在每执行一个任务时创建一个新的线程，不具有线程缓存机制，因为创建一个新的线程比复用一个线程更耗时耗力，
         * 虽然使用Schedulers.io()的地方，都可以使用Schedulers.newThread()，但是，Schedulers.newThread()
         * 的效率没有Schedulers.io()高。
         * Schedulers.computation()：
         * 用于CPU 密集型计算任务，即不会被 I/O 等操作限制性能的耗时操作，例如xml,json文件的解析，Bitmap图片的压缩取样等，
         * 具有固定的线程池，大小为CPU的核数。不可以用于I/O操作，因为I/O操作的等待时间会浪费CPU。
         * Schedulers.trampoline()：
         * 在当前线程立即执行任务，如果当前线程有任务在执行，则会将其暂停，等插入进来的任务执行完之后，
         * 再将未完成的任务接着执行。
         * Schedulers.single()：
         * 拥有一个线程单例，所有的任务都在这一个线程中执行，当此线程中有任务执行时，其他任务将会按照先进先出的顺序依次执行。
         * Scheduler.from(@NonNull Executor executor)：
         * 指定一个线程调度器，由此调度器来控制任务的执行策略。
         * AndroidSchedulers.mainThread()：
         * 在Android UI线程中执行任务，为Android开发定制。
         * 注：
         * 在RxJava2中，废弃了RxJava1中的Schedulers.immediate( )
         * 在RxJava1中，Schedulers.immediate( )的作用为在当前线程立即执行任务，功能等同于RxJava2中的Schedulers.trampoline( )。
         * 而Schedulers.trampoline( )在RxJava1中的作用是当其它排队的任务完成后，在当前线程排队开始执行接到的任务，
         * 有点像RxJava2中的Schedulers.single()，但也不完全相同，因为Schedulers.single()不是在当前线程而是在一个
         * 线程单例中排队执行任务。
         *
         */

        //一次subscribeOn和一次observeOn
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("发射线程：" + Thread.currentThread().getName() + "---->发射" + i);
                            Thread.sleep(1000);
                            emitter.onNext(String.valueOf(i));
                        }
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())       //设置发布线程使用Schedulers.io()线程中发布数据
                .observeOn(Schedulers.newThread())  //设置观察者在Schedulers.newThread()的线程中处理数据
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("接收线程：" + Thread.currentThread().getName() + "---->接收" + s);
                    }
                });
        //等待数据处理完毕
        Thread.sleep(6000);

        System.out.println("=========================分割线============================");

        //使用两次subscribeOn和一次observeOn
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("发射线程：" + Thread.currentThread().getName() + "---->发射" + i);
                            Thread.sleep(1000);
                            emitter.onNext(String.valueOf(i));
                        }
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())       //设置发布线程使用Schedulers.io()线程中发布数据
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(@NonNull String s) throws Exception {
                        System.out.println("处理线程：" + Thread.currentThread().getName() + "---->处理" + s);
                        return Integer.valueOf(s);
                    }
                })
                .subscribeOn(Schedulers.newThread()) //再次指定线程（不会起作用）
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        System.out.println("接收线程：" + Thread.currentThread().getName() + "---->接收" + s);
                    }
                });

        //等待数据处理完毕
        Thread.sleep(6000);
        System.out.println("=========================分割线============================");
        //使用一次subscribeOn和两次observeOn
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("发射线程：" + Thread.currentThread().getName() + "---->发射" + i);
                            Thread.sleep(1000);
                            emitter.onNext(String.valueOf(i));
                        }
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())       //设置发布线程使用Schedulers.io()线程中发布数据
                .observeOn(Schedulers.newThread())  //处理线程会使用newThread调度器
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(@NonNull String s) throws Exception {
                        System.out.println("处理线程：" + Thread.currentThread().getName() + "---->处理" + s);
                        return Integer.valueOf(s);
                    }
                })
                .observeOn(Schedulers.single()) //再次设定会使 观察者使用single调度器
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        System.out.println("接收线程：" + Thread.currentThread().getName() + "---->接收" + s);
                    }
                });
        //等待数据处理完毕
        Thread.sleep(6000);
        System.out.println("=========================分割线============================");

    }


    @Test
    public void trampoline() throws Exception {

        //Schedulers.trampoline()
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("发射线程：" + Thread.currentThread().getName() + "---->发射" + i);
                            Thread.sleep(1000);
                            emitter.onNext(String.valueOf(i));
                        }
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())//设置发布线程使用Schedulers.io()线程中发布数据
                .observeOn(Schedulers.trampoline()) //观察者使用trampoline调度器 会保证FIFO
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Thread.sleep(2000); //等待2秒
                        System.out.println("接收线程：" + Thread.currentThread().getName() + "---->接收" + s);
                    }
                });
        Thread.sleep(20000);
    }

    @Test
    public void single() throws Exception{

        //Schedulers.single()
        /**
         * 结果就是 先发布完成 再挨个处理Observer来接收数据
         */
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("发射线程：" + Thread.currentThread().getName() + "---->发射" + i);
                            Thread.sleep(1000);
                            emitter.onNext(String.valueOf(i));
                        }
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.single())//设置发布线程使用Schedulers.single()线程中发布数据
                .observeOn(Schedulers.single()) //观察者使用Schedulers.single()调度器
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Thread.sleep(2000); //等待2秒
                        System.out.println("接收线程：" + Thread.currentThread().getName() + "---->接收" + s);
                    }
                });
        Thread.sleep(20000);

    }


}
