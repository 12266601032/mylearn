package com.example.guice.module;

/**
 */
public class DatabaseStorage implements Storage {
    @Override
    public void store(String uniqueId, Data data) {
        System.out.println("store data to database...");
    }

    @Override
    public Data retrieve(String uniqueId) {
        System.out.println("retrieve data from database...");
        return new Data();
    }
}
