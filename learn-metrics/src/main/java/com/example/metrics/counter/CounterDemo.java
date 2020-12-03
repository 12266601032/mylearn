package com.example.metrics.counter;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import java.util.concurrent.TimeUnit;

public class CounterDemo {

  private final Counter invokeCounter;


  public CounterDemo(MetricRegistry metrics) {
    this.invokeCounter = new Counter();
    metrics.register(CounterDemo.class.getName() + ".invokeCounter", this.invokeCounter);
  }

  public void invoke() {
    invokeCounter.inc();
  }

  public static void main(String[] args) throws InterruptedException {
    final MetricRegistry metrics = new MetricRegistry();
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
        .build();
    reporter.start(1, TimeUnit.SECONDS);
    CounterDemo counterDemo = new CounterDemo(metrics);
    Thread.sleep(2000);
    counterDemo.invoke();
    Thread.sleep(3000);
  }

}
