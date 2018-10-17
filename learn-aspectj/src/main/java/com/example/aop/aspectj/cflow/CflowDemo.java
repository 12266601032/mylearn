package com.example.aop.aspectj.cflow;

public class CflowDemo {
  public void foo(){
    System.out.println("foo ....");
  }
  public void bar(){
    foo();
    System.out.println("bar ....");
  }

  public static void main(String[] args) {
    //bar()'s clfow start...
    new CflowDemo().bar();
  }
}
