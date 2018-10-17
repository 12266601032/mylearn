package com.example.aop.aspectj.advice;

public class AdviceDemo {

  public int normal(int i) {
    System.out.println("execute normal:" + i);
    return i;
  }

  public int error(int i) throws ArithmeticException {
    return i / 0;
  }

  public static void main(String[] args) {
    new AdviceDemo().normal(10);
    try {
      new AdviceDemo().error(0);
    } catch (Exception e) {

    }
  }
}
