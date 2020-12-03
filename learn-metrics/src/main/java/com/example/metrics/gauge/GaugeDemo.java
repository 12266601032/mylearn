package com.example.metrics.gauge;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.example.metrics.counter.CounterDemo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

public class GaugeDemo {

  private Cache<String, Object> cache = CacheBuilder.newBuilder().build();

  public GaugeDemo(MetricRegistry metrics) {
    metrics.register(MetricRegistry.name(GaugeDemo.class, "cache", "size"), new Gauge<Long>() {
      @Override
      public Long getValue() {
        return cache.size();
      }
    });
  }

  public void addCache(String key, Object value) {
    cache.put(key, value);
  }

  public static void main(String[] args) throws Exception {
    final MetricRegistry metrics = new MetricRegistry();
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
        .build();
    reporter.start(1, TimeUnit.SECONDS);
    GaugeDemo counterDemo = new GaugeDemo(metrics);
    Thread.sleep(2000);
    counterDemo.addCache("1", 123);
    Thread.sleep(3000);
  }

}
