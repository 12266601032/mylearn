package com.example.java.lang;

public class RuntimeDemo {

  public static void main(String[] args) {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("shutdown hook executed");
    }));
  }

}
