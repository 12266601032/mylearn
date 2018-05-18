package com.example.lcc.basic.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;

import java.lang.reflect.Method;

public class SpringAopDemo {

    @Test
    public void name() throws Exception {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(false);
        proxyFactory.setFrozen(false);
        proxyFactory.setOpaque(false);
        proxyFactory.setExposeProxy(true);
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
                System.out.println("拦截环绕通知-前");
                Object retVal = invocation.proceed();
                System.out.println("拦截环绕通知-后");
                return retVal;
            }
        });
        proxyFactory.addAdvice(new ThrowsAdviceInterceptor(new ThrowingAdvice()));
        proxyFactory.setTarget(new TargetObject());
        Say proxy = (Say) proxyFactory.getProxy();
        proxy.doSay();

    }

    interface Say {
        void doSay();
    }

    class TargetObject implements Say {
        public void doSay() {
            System.out.println("hello word.");
            //throw new RuntimeException("异常了。。。");
        }
    }

    public class ThrowingAdvice {
        public void afterThrowing(Throwable t) {
            System.out.println("异常通知：" + t.getMessage());
        }
    }
}
