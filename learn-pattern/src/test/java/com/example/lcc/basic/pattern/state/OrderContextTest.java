package com.example.lcc.basic.pattern.state;


import com.example.lcc.basic.pattern.state.order.state.OrderStateContext;

/**
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
