package com.example.lcc.basic.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

/**
 * Created by liucongcong on 2018/3/13.
 */
public class StorageModule implements Module {



    @Override
    public void configure(Binder binder) {
        binder.bind(Storage.class)
                .annotatedWith(Names.named("file"))
                .to(FileStorage.class)
                .in(Scopes.SINGLETON);
        binder.bind(Storage.class)
                .annotatedWith(Names.named("database"))
                .to(DatabaseStorage.class)
                .in(Scopes.SINGLETON);
    }
}
