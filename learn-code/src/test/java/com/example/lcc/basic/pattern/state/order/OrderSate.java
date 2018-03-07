package com.example.lcc.basic.pattern.state.order;


import com.example.lcc.basic.pattern.state.order.state.OrderStateContext;

public interface OrderSate {
    boolean isCurrentState(OrderStateContext.Order order);
    void cancel();
    void register();
    void grant();
    void ship();
    void invoice();
    void addOrderLine();
    String getState();
}
