package com.example.guice.module;

/**
 */
public class FileStorage implements Storage {
    @Override
    public void store(String uniqueId, Data data) {
        System.out.println("store data to field...");
    }

    @Override
    public Data retrieve(String uniqueId) {
        System.out.println("retrieve data from field...");
        return null;
    }
}
