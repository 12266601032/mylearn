package com.example.java.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FuturePatternDemo {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    Future<?> future = executorService.submit(() -> {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("complete");
    });
    System.out.println(future instanceof FutureTask);
    executorService.shutdown();
  }


}
