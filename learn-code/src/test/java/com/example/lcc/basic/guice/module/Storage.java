package com.example.lcc.basic.guice.module;

/**
 * Created by liucongcong on 2018/3/13.
 */
public interface Storage {

    public void store(String uniqueId, Data data);

    public Data retrieve(String uniqueId);
}
