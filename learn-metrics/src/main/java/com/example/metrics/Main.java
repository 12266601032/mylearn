package com.example.metrics;

/**
 * Gauges: an instantaneous measurement of a discrete value.
 * <p>
 * Counters: a value that can be incremented and decremented. Can be used in queues to monitorize
 * the remaining number of pending jobs.
 * <p>
 * Meters: measure the rate of events over time. You can specify the rate unit, the scope of events
 * or event type.
 * <p>
 * Histograms: measure the statistical distribution of values in a stream of data.
 * <p>
 * Timers: measure the amount of time it takes to execute a piece of code and the distribution of
 * its duration.
 * <p>
 * Healthy checks: as his name suggests, it centralize our service’s healthy checks of external
 * systems.
 * <p>
 * Gauges: 用于瞬时值的测量，比如采集某集合内的元素个数
 * <p>
 * Counters: 计数器
 * <p>
 * Meters：单位时间内事件发生的速率，比如TPS
 * <p>
 * Histograms：Histogram统计数据流量的分布情况。比如最小值，最大值，中间值，还有中位数，75百分位, 90百分位, 95百分位, 98百分位, 99百分位, 和
 * 99.9百分位的值(percentiles)。
 * <p>
 * Timers：其实是 Histogram 和 Meter 的结合， histogram 某部分代码/调用的耗时， meter统计TPS。
 * <p>
 * Healthy checks：
 */
public class Main {

  public static void main(String[] args) {

  }

}
