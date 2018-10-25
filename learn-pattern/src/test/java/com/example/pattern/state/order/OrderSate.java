package com.example.pattern.state.order;


import com.example.pattern.state.order.state.OrderStateContext.Order;

public interface OrderSate {
    boolean isCurrentState(Order order);
    void cancel();
    void register();
    void grant();
    void ship();
    void invoice();
    void addOrderLine();
    String getState();
}
