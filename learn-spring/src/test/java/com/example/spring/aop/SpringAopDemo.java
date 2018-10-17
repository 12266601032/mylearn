package com.example.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.TargetClassAware;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

public class SpringAopDemo {

    public static void main(String[] args) throws Exception {
        demo1();
    }

    public static void demo1() throws Exception {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(false);
        proxyFactory.setFrozen(false);
        proxyFactory.setOpaque(false); //设置将aop配置透明化处理
        proxyFactory.setExposeProxy(true); //给被切入的方法暴露proxy对象
        proxyFactory.addAdvice(new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("前置通知");
            }

        }));
        proxyFactory.addAdvice(new AfterReturningAdviceInterceptor(new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println("正常返回通知");
            }

        }));
        proxyFactory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("环绕通知-前");
                Object retVal = invocation.proceed();
                System.out.println("环绕通知-后");
                return retVal;
            }
        });
        proxyFactory.addAdvice(new ThrowsAdviceInterceptor(new ThrowingAdvice()));
        proxyFactory.setTarget(new TargetObject());
        //proxyFactory.setInterfaces(Say.class);
        Say proxy = (Say) proxyFactory.getProxy();
        proxy.doSay();

    }

    interface Say {
        void doSay();
    }

    static class TargetObject implements Say {
        public void doSay() {
            Object currentProxy = AopContext.currentProxy();
            System.out.println("\t\t\t当前代理：" + currentProxy.toString());
            Advised advised = (Advised) currentProxy;
            System.out.println("\t\t\t设置AOP配置透明化处理，AOP配置信息：" + Arrays.toString(advised.getAdvisors()));
            System.out.println("\t\t\tproxy class:" + currentProxy.getClass());
            System.out.println("\t\t\ttarget class:" + ((TargetClassAware) currentProxy).getTargetClass());

            System.out.println("hello word.");
            //throw new RuntimeException("异常了。。。");
        }
    }

    static public class ThrowingAdvice {
        public void afterThrowing(Throwable t) {
            System.out.println("异常通知：" + t.getMessage());
        }
    }
}
