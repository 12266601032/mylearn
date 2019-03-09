package com.example.java.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class ThreadPoolExecutorDemo {

  @Test
  public void name() throws InterruptedException {

    ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 20, 60, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>());
    executor.submit(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("complete");
      }
    });
    executor.shutdown();
    executor.awaitTermination(30, TimeUnit.SECONDS);

  }
}
