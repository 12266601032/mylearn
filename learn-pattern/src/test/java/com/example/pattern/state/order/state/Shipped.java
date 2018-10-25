package com.example.pattern.state.order.state;


import com.example.pattern.state.order.OrderSate;
import com.example.pattern.state.order.UnspportOrderState;

/**
 */
class Shipped extends UnspportOrderState implements OrderSate {

    public Shipped(OrderStateContext context) {
        super(context);
    }

    @Override
    public void invoice() {
        getContext().changeState(getContext().getInvoicedState());
    }

    @Override
    public String getState() {
        return "Shipped";
    }

    @Override
    public boolean isCurrentState(OrderStateContext.Order order) {
        return getState().equals(order.getStatus());
    }
}
