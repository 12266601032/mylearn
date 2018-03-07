package com.example.lcc.basic.pattern.state;

public class OrderService {


    public void cancel(Order order){
        this.changeStatus(order,"Cancelled");
    }

    public void register(Order order){
        this.changeStatus(order,"Registered");
    }
    public void grant(Order order){
        this.changeStatus(order,"Granted");
    }
    public void ship(Order order){
        this.changeStatus(order,"Shiped");
    }
    public void invoice(Order order){
        this.changeStatus(order,"invoiced");
    }
    public void addOrderLine(Order order){
        this.changeStatus(order,"state.order.state.NewOrder");
    }

    public void changeStatus(Order order,String newStatus){

        System.out.println("订单从：" + order.getStatus() + " --> " + newStatus);
        //新建订单 只能到Registered 、 Cancelled 、state.order.state.NewOrder
        if("state.order.state.NewOrder".equals(order.getStatus())){
            if("Registered".equals(newStatus)){
                System.out.println("修改成功！");
            }else if("Cancelled".equals(newStatus)){
                System.out.println("修改成功！");
            }else if("state.order.state.NewOrder".equals(newStatus)){
                System.out.println("修改成功！");
            }else{
                System.out.println("修改失败！");
                //throw new RuntimeException("状态转换错误");
            }
        }
        //Registered订单 只能到 Granted、state.order.state.NewOrder 、 Cancelled
        if("Registered".equals(order.getStatus())){
            if("state.order.state.NewOrder".equals(newStatus)){
                System.out.println("修改成功！");
            }else if("Cancelled".equals(newStatus)){
                System.out.println("修改成功！");
            }else if("Granted".equals(newStatus)){
                System.out.println("修改成功！");
            }else{
                System.out.println("修改失败！");
                //throw new RuntimeException("状态转换错误");
            }
        }

        //Granted 订单只能到 Shiped、state.order.state.NewOrder 、 Cancelled
        if("Granted".equals(order.getStatus())){
            if("state.order.state.NewOrder".equals(newStatus)){
                System.out.println("修改成功！");
            }else if("Cancelled".equals(newStatus)){
                System.out.println("修改成功！");
            }else if("Shiped".equals(newStatus)){
                System.out.println("修改成功！");
            }else{
                System.out.println("修改失败！");
                //throw new RuntimeException("状态转换错误");
            }
        }
        //Shiped 订单只能到 invoiced
        if("Shiped".equals(order.getStatus())){
            if("invoiced".equals(newStatus)){
                System.out.println("修改成功！");
            }else{
                System.out.println("修改失败！");
                //throw new RuntimeException("状态转换错误");
            }
        }
        //invoiced 订单不可再修改状态
        if("invoiced".equals(order.getStatus())){
            System.out.println("修改失败！");
            //throw new RuntimeException("状态转换错误");
        }

        //Cancelled 订单不可再修改状态
        if("Cancelled".equals(order.getStatus())){
            System.out.println("修改失败！");
            //throw new RuntimeException("状态转换错误");
        }
        order.setStatus(newStatus);
    }


    public static class Order{

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
