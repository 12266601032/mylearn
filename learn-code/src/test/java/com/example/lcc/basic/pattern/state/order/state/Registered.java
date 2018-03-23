package com.example.lcc.basic.pattern.state.order.state;


import com.example.lcc.basic.pattern.state.order.OrderSate;
import com.example.lcc.basic.pattern.state.order.UnspportOrderState;

/**
 */
class Registered extends UnspportOrderState implements OrderSate {
    public Registered(OrderStateContext context) {
        super(context);
    }
    @Override
    public void grant() {
        getContext().changeState(getContext().getGrantedState());
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
    public String getState() {
        return "Registered";
    }

    @Override
    public boolean isCurrentState(OrderStateContext.Order order) {
        return getState().equals(order.getStatus());
    }
}
