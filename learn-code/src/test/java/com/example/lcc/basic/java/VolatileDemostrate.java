package com.example.lcc.basic.java;

/**
 */
public class VolatileDemostrate {

    static class ReorderExample{
        int a = 0;
        boolean flag = false;
        public void writer() {
            a = 1; // 1
            flag = true; // 2
        }
        public void reader() {
            if (flag) { // 3
                int i = a;
                if(i != 1){
                    System.out.println("a:" + i);
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ReorderExample example = new ReorderExample();
        for (int i = 0; i < 1000000; i++) {
            Thread writeThread = new Thread(() -> {
                example.writer();
            });
            Thread readThread = new Thread(() -> {
                example.reader();
            });
            writeThread.start();
            readThread.start();
            writeThread.join();
            readThread.join();
            example.a = 0;
            example.flag = false;
        }
        System.out.println("finish...");
    }
}
