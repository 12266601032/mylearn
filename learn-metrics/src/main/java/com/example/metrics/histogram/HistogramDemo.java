package com.example.metrics.histogram;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import java.util.concurrent.TimeUnit;

public class HistogramDemo {

  public static void main(String[] args) throws Exception {
    final MetricRegistry metrics = new MetricRegistry();
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
        .build();
    Histogram histogram = metrics.histogram("");
    histogram.update(1);
    histogram.update(5);
    histogram.update(2);
    histogram.update(7);
    histogram.update(9);
    reporter.start(1, TimeUnit.SECONDS);
    Thread.sleep(2000);
  }

}
