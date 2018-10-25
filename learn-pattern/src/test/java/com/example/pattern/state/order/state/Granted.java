package com.example.pattern.state.order.state;


import com.example.pattern.state.order.OrderSate;
import com.example.pattern.state.order.UnspportOrderState;

/**
 */
class Granted extends UnspportOrderState implements OrderSate {
    public Granted(OrderStateContext context) {
        super(context);
    }

    @Override
    public void addOrderLine() {
        getContext().changeState(getContext().getNewOrderState());
    }

    @Override
    public void cancel() {
        getContext().changeState(getContext().getCancelledState());
    }

    @Override
    public void ship() {
        getContext().changeState(getContext().getShipedState());
    }

    @Override
    public String getState(){
        return "Granted";
    }

    @Override
    public boolean isCurrentState(OrderStateContext.Order order) {
        return getState().equals(order.getStatus());
    }
}
