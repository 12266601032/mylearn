package com.example.lcc.basic.java;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class LockDemostrate {


    static final ReentrantLock lock = new ReentrantLock();

    @Test
    public void testJoin() throws Exception {

    }

    @Test
    public void testReentrantLock() throws InterruptedException {
        //默认是非公平锁
        lock.lock();
        try {
            lock.lock();
            printfInfo();
            new Thread(() -> {
                printfInfo();
                lock.lock();
                try {
                    System.out.println("twice get lock success.");
                } finally {
                    lock.unlock();
                }
            }, "Twice").start();
        } finally {
            lock.unlock();
            lock.unlock();
        }
        new Thread(() -> {
            printfInfo();
            lock.lock();
            try {
                System.out.println("unlocked get lock success.");
            } finally {
                lock.unlock();
            }
        }, "unlocked").start();
        System.exit(0);
    }

    private void printfInfo() {
        System.out.printf("Thread:%s,lock:%s,hold count:%d,queue length,wait queue length:%d.%n"
                , Thread.currentThread().getName(), lock, lock.getHoldCount(), lock.getQueueLength(), -1);
    }

    static class Mutex implements Lock {
        private final Sync sync = new Sync();

        @Override
        public void lock() {
            sync.acquire(1);
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            sync.acquireInterruptibly(1);
        }

        @Override
        public boolean tryLock() {
            return sync.tryAcquire(1);
        }

        @Override
        public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
            return sync.tryAcquireNanos(1, unit.toNanos(timeout));
        }

        @Override
        public void unlock() {
            sync.release(1);
        }

        @Override
        public Condition newCondition() {
            return sync.newCondition();
        }

        private static class Sync extends AbstractQueuedSynchronizer {
            // 是否处于占用状态
            protected boolean isHeldExclusively() {
                return getState() == 1;
            }

            // 当状态为0的时候获取锁
            public boolean tryAcquire(int acquires) {
                if (compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
                return false;
            }

            // 释放锁，将状态设置为0
            protected boolean tryRelease(int releases) {
                if (getState() == 0) throw new
                        IllegalMonitorStateException();
                setExclusiveOwnerThread(null);
                setState(0);
                return true;
            }

            // 返回一个Condition，每个condition都包含了一个condition队列
            Condition newCondition() {
                return new ConditionObject();
            }
        }
    }
}
