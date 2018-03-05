package com.example.lcc.basic.guava;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

/**
 * Created by liucongcong on 2018/2/8.
 */
public class EventBusDemostrate {


    @Test
    public void testEventbus(){
        //创建事件总线
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
        System.out.print(SubClassSubscriber.class);
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
        @Subscribe
        public void listen(DemoEvent event){
            System.out.println(event.getMessage());
        }
    }

    public static class SubClassSubscriber extends EventSubscriber{
        @Subscribe
        @AllowConcurrentEvents
        public void subClassListen(DemoEvent event){
            System.out.printf("print from nested class subscriber message is: %s.%n",event.getMessage());
        }
    }
}
