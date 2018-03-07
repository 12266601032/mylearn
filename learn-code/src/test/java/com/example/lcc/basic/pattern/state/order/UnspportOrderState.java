package com.example.lcc.basic.pattern.state.order;


import com.example.lcc.basic.pattern.state.order.state.OrderStateContext;

/**
 * Created by liucongcong on 2018/1/10.
 */
public abstract class UnspportOrderState implements OrderSate{

    protected OrderStateContext context;

    public UnspportOrderState(OrderStateContext context) {
        this.context = context;
    }

    @Override
    public void cancel() {
        System.out.println("修改失败！");
        //throw new UnsupportedOperationException();
    }

    @Override
    public void register() {
        System.out.println("修改失败！");
        //throw new UnsupportedOperationException();
    }

    @Override
    public void grant() {
        System.out.println("修改失败！");
        //throw new UnsupportedOperationException();
    }

    @Override
    public void ship() {
        System.out.println("修改失败！");
        //throw new UnsupportedOperationException();
    }

    @Override
    public void invoice() {
        System.out.println("修改失败！");
        //throw new UnsupportedOperationException();
    }

    @Override
    public void addOrderLine() {
        System.out.println("修改失败！");
        //throw new UnsupportedOperationException();
    }

    protected OrderStateContext getContext(){
        return this.context;
    }
}
