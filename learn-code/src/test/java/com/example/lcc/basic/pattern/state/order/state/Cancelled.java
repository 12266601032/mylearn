package com.example.lcc.basic.pattern.state.order.state;


import com.example.lcc.basic.pattern.state.order.OrderSate;
import com.example.lcc.basic.pattern.state.order.UnspportOrderState;

/**
 * Created by liucongcong on 2018/1/10.
 */
class Cancelled extends UnspportOrderState implements OrderSate {
    public Cancelled(OrderStateContext context) {
        super(context);
    }

    @Override
    public String getState() {
        return "Cancelled";
    }
    @Override
    public boolean isCurrentState(OrderStateContext.Order order) {
        return getState().equals(order.getStatus());
    }
}
