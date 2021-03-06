package com.example.guice.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

/**
 */
public class StorageModule implements Module {



    @Override
    public void configure(Binder binder) {
        binder.bind(Storage.class)
                .annotatedWith(Names.named("field"))
                .to(FileStorage.class)
                .in(Scopes.SINGLETON);
        binder.bind(Storage.class)
                .annotatedWith(Names.named("database"))
                .to(DatabaseStorage.class)
                .in(Scopes.SINGLETON);
    }
}
