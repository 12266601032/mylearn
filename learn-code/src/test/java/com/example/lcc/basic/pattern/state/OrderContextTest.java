package com.example.lcc.basic.pattern.state;


import com.example.lcc.basic.pattern.state.order.state.OrderStateContext;

/**
 * Created by liucongcong on 2018/1/10.
 */
public class OrderContextTest {
    public static void main(String[] args){
        OrderStateContext.Order order = new OrderStateContext.Order();
        order.setStatus("NewOrder");
        OrderStateContext.of(order).register().grant().ship().invoice();
        System.out.println(order.getStatus());
        System.out.println(OrderStateContext.of(order).cancel().getOrder().getStatus());
    }
}
