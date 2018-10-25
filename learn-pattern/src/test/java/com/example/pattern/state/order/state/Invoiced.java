package com.example.pattern.state.order.state;


import com.example.pattern.state.order.OrderSate;
import com.example.pattern.state.order.UnspportOrderState;

/**
 */
class Invoiced extends UnspportOrderState implements OrderSate {
    public Invoiced(OrderStateContext context) {
        super(context);
    }

    @Override
    public String getState() {
        return "Invoiced";
    }
    @Override
    public boolean isCurrentState(OrderStateContext.Order order) {
        return getState().equals(order.getStatus());
    }
}
