package com.example.pattern.state.order;


import com.example.pattern.state.order.state.OrderStateContext;

/**
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
