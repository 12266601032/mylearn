package com.example.lcc.basic.guice.module;

/**
 * Created by liucongcong on 2018/3/13.
 */
public class FileStorage implements Storage {
    @Override
    public void store(String uniqueId, Data data) {
        System.out.println("store data to file...");
    }

    @Override
    public Data retrieve(String uniqueId) {
        System.out.println("retrieve data from file...");
        return null;
    }
}
