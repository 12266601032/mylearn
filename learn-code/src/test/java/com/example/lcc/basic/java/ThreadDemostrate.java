package com.example.lcc.basic.java;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 */
public class ThreadDemostrate {



    /**
     * 方式1
     * 利用join来实现主线程等待子线程任务结束。
     * 原理是通过wait(0) 让主线程永远等待知道当前任务结束
     * <pre>{@code
     * thread1.start();
     * thread2.start();
     * thread1.join();
     * thread2.join();
     * }<pre/>
     *
     * @throws Exception
     */
    @Test
    public void waitTaskWithJoin() throws Exception {
        Thread task1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.printf("Thread:%s,execute complete.%n", Thread.currentThread().getName());
        }, "task1");
        Thread task2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.printf("Thread:%s,execute complete.%n", Thread.currentThread().getName());
        }, "task2");
        task1.start();
        task2.start();
        task1.join();
        System.out.println("join 1 complete");
        task2.join();
        System.out.println("join 2 complete");
    }


   static CountDownLatch c = new CountDownLatch(2);
    /**
     * 方式2
     * 通过CountDownLatch来计数完成一个点countDown一下
     * 当等于0时完成
     * @throws Exception
     */
    @Test
    public void waitTaskWithCountDownLatch() throws Exception {
        Thread task1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            c.countDown();
            System.out.printf("Thread:%s,execute complete.%n", Thread.currentThread().getName());
        }, "task1");
        Thread task2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            c.countDown();
            System.out.printf("Thread:%s,execute complete.%n", Thread.currentThread().getName());
        }, "task2");
        task1.start();
        task2.start();
        c.await();
        System.out.printf("task execute is finished.");
    }


    @Test
    public void testThread() throws Exception {
        Thread parent = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            new Thread(() -> {
                try {
                    System.out.println("child sleeping...");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                System.out.println("over...");
            }).start();
        }, "parent");
        parent.start();
        parent.join();
        System.out.println("main sleeping...");
        Thread.sleep(6000);
        System.out.println("finish...");
    }
}
