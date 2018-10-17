package com.example.aop.aspectj.cflow;

public aspect CflowDemoAspect {

  pointcut barPoint(): execution(* bar());
  pointcut fooPoint(): execution(* foo());
  pointcut barCflow(): cflow(barPoint()) && !within(com.example.aop.aspectj.cflow.CflowDemoAspect);
  pointcut barCflowbelow(): cflowbelow(barPoint()) && !within(com.example.aop.aspectj.cflow.CflowDemoAspect);
  pointcut barAndFooCflow(): cflow(barPoint()) && cflow(fooPoint()) && !within(com.example.aop.aspectj.cflow.CflowDemoAspect);


  before(): barCflow(){
    /**
     * cflow为匹配切入点的P的所有后续要执行的连接点join point 包含切入点自身P
     */
    System.out.println("barCflow - Enter:" + thisJoinPoint);
  }

  before(): barCflowbelow(){
    /**
     * cflowblow为匹配切入点P的所有后续要执行的连接点join point 不包含切入点自身P
     */
    System.out.println("barCflowbelow - Enter:" + thisJoinPoint);
  }

  before(): barAndFooCflow(){
    /**
     * 取连接点交集
     */
    System.out.println("barAndFooCflow - Enter:" + thisJoinPoint);
  }


}
