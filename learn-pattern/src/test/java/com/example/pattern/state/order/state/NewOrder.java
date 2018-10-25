package com.example.pattern.state.order.state;


import com.example.pattern.state.order.OrderSate;
import com.example.pattern.state.order.UnspportOrderState;

/**
 */
class NewOrder extends UnspportOrderState implements OrderSate {


    public NewOrder(OrderStateContext context) {
        super(context);
    }

    @Override
    public void addOrderLine() {
        getContext().changeState(this);
    }

    @Override
    public void register() {
        getContext().changeState(getContext().getRegisteredState());
    }

    @Override
    public void cancel() {
        getContext().changeState(getContext().getCancelledState());
    }

    @Override
    public String getState() {
        return "NewOrder";
    }

    @Override
    public boolean isCurrentState(OrderStateContext.Order order) {
        return getState().equals(order.getStatus());
    }
}
