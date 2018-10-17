package com.example.aop.aspectj.advice;

import org.aspectj.lang.Aspects;

public aspect AdviceDemoAspect {

  pointcut normal(int i): execution(int normal(..)) && args(i);
  pointcut error(int i): execution(int error(..)) && args(i);

  before(int i): normal(i){
    System.out.println("before advice:" + i);
  }

  after(int i) returning: normal(i){
    System.out.println("after returning advice:" + i);
  }

  after() returning: normal(int){
    System.out.println("after returning advice 2");
  }

  after(int i) throwing (Exception e): error(i){
    System.out.println("after throwing advice:" + i);
  }

  after() throwing (Exception e): error(int){
    System.out.println("after throwing advice 2");
  }

  after(): error(int){
    System.out.println("after advice...");
  }

  int around(int i): error(i){
    System.out.println("around ");
    return proceed(i + 100);
  }

}
