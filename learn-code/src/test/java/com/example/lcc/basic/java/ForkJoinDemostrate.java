package com.example.lcc.basic.java;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liucongcong on 2018/2/10.
 */
@Slf4j
public class ForkJoinDemostrate {


    @Test
    public void testCountTask() throws ExecutionException, InterruptedException {
        ForkJoinTask<Integer> task = ForkJoinPool.commonPool().submit(new CountTask(1, 100));
        task.isCompletedAbnormally();
        task.isCompletedNormally();
        Integer total = task.get();
        System.out.printf("The sum of total is:%d",total);
    }



    /**
     * 该例子计算start,start+1,start+2...+end的和
     * 原理是将start ~ end 根据THRESHOLD进行任务分割（fork），知道任务粒度<=THRESHOLD再进行计算
     * 然后再将结果合并（join）
     */
    public class CountTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            //如果任务分割到了预计的粒度就开始计算
            boolean canCompute = (end - start) <= THRESHOLD;
            if (canCompute) {
                log.debug("start counting the sum,start num:{},end num:{}.", start, end);
                //计算
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                //分割
                int middle = (start + end) / 2;
                log.debug("start fork  the task.start num:{},end num:{},middle is:{}.", start, end, middle);
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);
                //执行子任务
                leftTask.fork();
                rightTask.fork();
                log.debug("start join the task...");
                //合并结果
                Integer leftResult = leftTask.join();
                Integer rightResult = rightTask.join();
                sum = leftResult + rightResult;
                log.debug("join complete,left result is:{},right result is:{}.,sum of left and right is:{}.", leftResult, rightResult, sum);
            }
            return sum;
        }
    }


    private static final AtomicInteger count = new AtomicInteger(0);


    @Test
    public void testGenerateTask() throws Exception {
        List<GenerateTask> tasks = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            GenerateTask task = new GenerateTask();
            tasks.add(task);
        }
        ForkJoinPool commonPool = new ForkJoinPool(12);
        log.debug("start execute GenerateTask...");
        commonPool.execute(new GenerateAction(tasks));
        //如果无空闲工作线程,让当前线程一起帮助完成任务。
        commonPool.awaitQuiescence(0, TimeUnit.NANOSECONDS);
        log.debug("finish... pool state:{}.",commonPool);
    }
    @Test
    public void testGenerateTask2() throws Exception {
        List<GenerateTask> tasks = Lists.newArrayList();
        for (int i = 0; i < 10000000; i++) {
            GenerateTask task = new GenerateTask();
            tasks.add(task);
        }
        ForkJoinPool commonPool = new ForkJoinPool(12);
        log.debug("start execute GenerateTask...");
        GenerateAction action = new GenerateAction(tasks);
        commonPool.execute(action);
        while(!action.isDone()){
            System.out.printf("pool size: %s,ActiveThreadCount:%s, ,%n"
                    ,commonPool.getPoolSize()
                    ,commonPool.getActiveThreadCount()
            );
            System.out.println(commonPool.getQueuedTaskCount()+"----11111--------------------------");
            if(!commonPool.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS)){
                Thread.sleep(1000);
            }
        }
        log.debug("finish... pool state:{}.",commonPool);
    }

    static class GenerateTask{
        public void doWork(){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            int current = count.addAndGet(1);
            log.debug("generate working...,count number is:{}.",current);
        }
    }

    static class GenerateAction extends RecursiveAction{

        //设计单个任务量的阀值为50 大于该数字则继续细分
        private static final int THRESHOLD = 5;
        private List<GenerateTask> taskList;


        public GenerateAction(List<GenerateTask> taskList) {
            Objects.requireNonNull(taskList);
            this.taskList = taskList;
        }

        @Override
        protected void compute() {

            boolean needSplit = taskList.size() > THRESHOLD; //判断任务量是否大于阀值
            if(needSplit){
                //任务分割
                int middle = taskList.size() / 2;
                log.debug("Too many tasks need to be broken up.Current quantity:{},then left is:0-{} and right is:{}-{}."
                                    ,taskList.size(),middle,(middle + 1),taskList.size());
                GenerateAction leftAction = new GenerateAction(taskList.subList(0, middle));
                GenerateAction rightAction = new GenerateAction(taskList.subList(middle, taskList.size()));
                //执行子任务
                leftAction.fork();
                rightAction.fork();
                leftAction.join();
                rightAction.join();
            }else{
                log.debug("start execute task...");
                //执行任务
                taskList.forEach((e) -> e.doWork());
            }
        }
    }
}
