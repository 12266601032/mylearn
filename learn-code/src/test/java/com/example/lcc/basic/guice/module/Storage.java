package com.example.lcc.basic.guice.module;

/**
 */
public interface Storage {

    public void store(String uniqueId, Data data);

    public Data retrieve(String uniqueId);
}
