package com.example.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.Test;

public class AopBehaviorDemo {


  @Test
  public void name() {
    new ManImpl().say("123");
    System.out.println("=================================");
    new EnhanceManImpl().say("123");
    System.out.println("=================================");
  }

  @Test
  public void proxyInvoke() {
    Man proxy = (Man) Proxy.newProxyInstance(getClass().getClassLoader(),
        new Class[]{Man.class},
        new MyInvocationHandler(new EnhanceManImpl())
    );

    proxy.say("abc");
  }

  class MyInvocationHandler implements InvocationHandler {

    Object proxyTarget;

    public MyInvocationHandler(Object proxyTarget) {
      this.proxyTarget = proxyTarget;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

      //前置通知
      System.out.println("方法调用前。。。。");
      //调用目标对象的方法
      try {
        Object result = method.invoke(proxyTarget, args);
        //正常返回通知
        System.out.println("方法调用后。。。。");
        return result;
      } catch (Throwable e) {
        //异常返回通知
        System.out.println("方法异常后。。。。");
        throw e;
      } finally {
        //finally返回通知
        System.out.println("不管异常不异常。。。。");
      }
    }
  }


  interface Man {

    void say(String message);

  }

  class EnhanceManImpl implements Man {

    @Override
    public void say(String message) {
      //前置通知
      System.out.println("前置调用。。。");
      new ManImpl().say(message);
      //后置通知
      System.out.println("后置调用。。。");
      throw new RuntimeException();
    }
  }

  class ManImpl implements Man {

    @Override
    public void say(String message) {
      System.out.println("我说：" + message);
    }
  }

}
