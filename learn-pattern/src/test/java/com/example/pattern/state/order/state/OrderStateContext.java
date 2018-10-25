package com.example.pattern.state.order.state;


import com.example.pattern.state.order.OrderSate;

/**
 */
public class OrderStateContext {

    private Order order;
    private OrderSate newOrderState;
    private OrderSate registeredState;
    private OrderSate grantedState;
    private OrderSate shipedState;
    private OrderSate invoicedState;
    private OrderSate cancelledState;
    private OrderSate currentState;

    private OrderStateContext(Order order) {
        this.order = order;
        this.newOrderState = new NewOrder(this);
        assignCurrentState(newOrderState);
        this.registeredState = new Registered(this);
        assignCurrentState(registeredState);
        this.grantedState = new Granted(this);
        assignCurrentState(grantedState);
        this.shipedState = new Shipped(this);
        assignCurrentState(shipedState);
        this.invoicedState = new Invoiced(this);
        assignCurrentState(invoicedState);
        this.cancelledState = new Cancelled(this);
        assignCurrentState(cancelledState);
    }

    public Order getOrder() {
        return order;
    }

    OrderSate getCurrentState() {
        return currentState;
    }

    void changeState(OrderSate newState) {
        this.currentState = newState;
        this.order.setStatus(newState.getState());
    }

    private void assignCurrentState(OrderSate state) {
        if (state.isCurrentState(order)) {
            this.currentState = state;
        }
    }

    public static OrderStateContext of(Order order) {
        OrderStateContext context = new OrderStateContext(order);
        return context;
    }


    OrderSate getNewOrderState() {
        return newOrderState;
    }

    OrderSate getRegisteredState() {
        return registeredState;
    }

    OrderSate getGrantedState() {
        return grantedState;
    }

    OrderSate getShipedState() {
        return shipedState;
    }

    OrderSate getInvoicedState() {
        return invoicedState;
    }

    OrderSate getCancelledState() {
        return cancelledState;
    }

    public OrderStateContext cancel() {
        String oldState = currentState.getState();
        currentState.cancel();
        String newState = currentState.getState();
        System.out.println("订单从：" + oldState + " --> " + newState);
        return this;
    }

    public OrderStateContext register() {
        String oldState = currentState.getState();
        currentState.register();
        String newState = currentState.getState();
        System.out.println("订单从：" + oldState + " --> " + newState);
        return this;
    }

    public OrderStateContext grant() {
        String oldState = currentState.getState();
        currentState.grant();
        String newState = currentState.getState();
        System.out.println("订单从：" + oldState + " --> " + newState);
        return this;
    }

    public OrderStateContext ship() {
        String oldState = currentState.getState();
        currentState.ship();
        String newState = currentState.getState();
        System.out.println("订单从：" + oldState + " --> " + newState);
        return this;
    }

    public OrderStateContext invoice() {
        String oldState = currentState.getState();
        currentState.invoice();
        String newState = currentState.getState();
        System.out.println("订单从：" + oldState + " --> " + newState);
        return this;
    }

    public OrderStateContext addOrderLine() {
        currentState.addOrderLine();
        return this;
    }

    public static class Order {

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
