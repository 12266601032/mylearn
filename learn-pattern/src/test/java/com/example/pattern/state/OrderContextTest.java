package com.example.pattern.state;


import com.example.pattern.state.order.state.OrderStateContext;
import com.example.pattern.state.order.state.OrderStateContext.Order;

/**
 */
public class OrderContextTest {
    public static void main(String[] args){
        Order order = new Order();
        order.setStatus("NewOrder");
        OrderStateContext.of(order).register().grant().ship().invoice();
        System.out.println(order.getStatus());
        System.out.println(OrderStateContext.of(order).cancel().getOrder().getStatus());
    }
}
