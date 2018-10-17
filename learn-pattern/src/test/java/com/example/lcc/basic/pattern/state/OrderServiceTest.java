package com.example.lcc.basic.pattern.state;

import java.util.EnumMap;

public class OrderServiceTest {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        OrderService.Order order = new OrderService.Order();
        System.out.println("----------正常状态转换----------");
        order.setStatus("NewOrder");
        orderService.register(order);
        orderService.grant(order);
        orderService.ship(order);
        orderService.invoice(order);

        order.setStatus("Granted");
        orderService.cancel(order);
        order.setStatus("Registered");
        orderService.cancel(order);
        order.setStatus("NewOrder");
        orderService.cancel(order);

        System.out.println("----------异常状态转换----------");
        order.setStatus("invoiced");
        orderService.ship(order);
        order.setStatus("Cancelled");
        orderService.addOrderLine(order);
    }
}
