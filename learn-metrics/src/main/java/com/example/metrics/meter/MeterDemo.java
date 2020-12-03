package com.example.metrics.meter;

import com.codahale.metrics.Meter;

public class MeterDemo {

  public static void main(String[] args) {
    Meter meter = new Meter();
    meter.mark();
  }

}
