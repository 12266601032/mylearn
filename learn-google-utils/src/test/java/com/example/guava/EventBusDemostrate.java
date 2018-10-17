package com.example.guava;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

/**
 */
public class EventBusDemostrate {


    @Test
    public void testEventbus(){
        /**
         * 创建事件总线
         * 用来注册订阅事件的订阅者，都是以注解标记方法的形式来接收事件的订阅。
         * 以及处理事件，进行事件分发。其有几个重载构造方法
         * {@link EventBus#EventBus(com.google.common.eventbus.SubscriberExceptionHandler)}
         * 方法可以指定一个异常处理器，来处理订阅者处理事件时的异常。
         */
        EventBus testbus = new EventBus("testbus");
        /**
         * 注册订阅者
         * 该方法会根据传入对象解析到，@Subscribe注解标记到的公有，而且方法必须
         * 只能有一个参数的所有方法，注册为订阅者。（会根据订阅的事件类型进行分组存入到一到key为Event value为订阅者信息的形式）
         * 这个订阅关系可以被继承，即注册子类实例即可。
         */
        SubClassSubscriber subClassSubscriber = new SubClassSubscriber();
        testbus.register(subClassSubscriber);
        /**
         * 发布事件
         * 默认情况下是通过PerThreadQueuedDispatcher进行单线程异步转发处理
         */
        testbus.post(new DemoEvent("hello event bus."));
        //取消订阅
        testbus.unregister(SubClassSubscriber.class);
    }

    private static class DemoEvent {
        private String message;
        public DemoEvent(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    public static class EventSubscriber{

        /**
         * {@code @Subscribe} 注解的作用就是标记该方法是一个事件订阅者
         * 订阅者方法的约定是只能有一个参数，表示接收什么类型的事件。
         * 可以结合{@code @AllowConcurrentEvents}使用，表示该方法是线程安全的，允许接收并行事件。
         */
        @Subscribe
        public void listen(DemoEvent event){
            System.out.println(event.getMessage());
        }
    }

    public static class SubClassSubscriber extends EventSubscriber{
        @Subscribe
        @AllowConcurrentEvents
        public void subClassListen(DemoEvent event){
            System.out.printf("print from nested clazz subscriber message is: %s.%n",event.getMessage());
        }
    }
}
