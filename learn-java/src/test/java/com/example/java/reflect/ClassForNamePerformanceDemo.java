package com.example.java.reflect;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.xml.xpath.XPathFactory;
import org.junit.Assert;
import org.junit.Test;

public class ClassForNamePerformanceDemo {

  @Test
  public void name() throws Exception {
    int totalCount = 100000;
    long start = System.currentTimeMillis();
    for (int i = 0; i < totalCount; i++) {
      ServiceLoader<XPathFactory> load = ServiceLoader.load(XPathFactory.class);
      load.forEach(el -> {});
    }
    System.out.println("ServiceLoader:" + (System.currentTimeMillis() - start) + "ms");
    start = System.currentTimeMillis();
    for (int i = 0; i < totalCount; i++) {
      Class.forName("com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl");
    }
    System.out.println("Class.forName-SingleThread:" + (System.currentTimeMillis() - start) + "ms");

    ExecutorService executorService = Executors.newFixedThreadPool(100);
    start = System.currentTimeMillis();
    for (int i = 0; i < totalCount; i++) {
      executorService.submit(() -> {
        try {
          Class.forName("com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl");
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      });
    }
    executorService.shutdown();
    boolean termination = executorService.awaitTermination(30, TimeUnit.SECONDS);
    Assert.assertTrue(termination);
    System.out.println("Class.forName-100Thread:" + (System.currentTimeMillis() - start) + "ms");

    executorService = Executors.newFixedThreadPool(100);
    start = System.currentTimeMillis();
    for (int i = 0; i < totalCount; i++) {
      executorService.submit(() -> {
        try {
          getClass().getClassLoader()
              .loadClass("com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl");
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      });
    }
    executorService.shutdown();
    termination = executorService.awaitTermination(30, TimeUnit.SECONDS);
    Assert.assertTrue(termination);
    System.out.println("ClassLoader-100Thread:" + (System.currentTimeMillis() - start) + "ms");

    Map<String, Object> pool = new ConcurrentHashMap<>();
    pool.put("a", Class.forName("com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl"));
    executorService = Executors.newFixedThreadPool(100);
    start = System.currentTimeMillis();
    for (int i = 0; i < totalCount; i++) {
      executorService.submit(() -> {
        try {
          pool.get("a");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
      });
    }
    executorService.shutdown();
    termination = executorService.awaitTermination(30, TimeUnit.SECONDS);
    Assert.assertTrue(termination);
    System.out.println("ObjectPool-100Thread:" + (System.currentTimeMillis() - start) + "ms");
  }
}
