package com.example.lcc.basic.guice;

import com.example.lcc.basic.guice.module.Data;
import com.example.lcc.basic.guice.module.DatabaseStorage;
import com.example.lcc.basic.guice.module.Storage;
import com.example.lcc.basic.guice.module.StorageModule;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Map;

/**
 */
public class StorageClient {

    @Inject  //表示是需要注入的成员
    @Named("file") //会根据Key.get(Storage.clazz, Names.named("database")) 来注入
    private Storage s;


    @Test
    public void client(){
        /**
         * 根据指定模块 创建injector
         * 这里StorageModule 实现自 com.google.inject.Module
         * 用来维护一组binding 一个应用中也可以有多个module
         *
         * 给成员进行依赖注入，这里由于传入的实例对象，所以会直接忽略掉构造器注入的部分
         * injector.injectMembers(this);
         *
         */
        Injector injector = Guice.createInjector(new StorageModule());
        Storage storage = injector.getInstance(Key.get(Storage.class, Names.named("database")));
        injector.injectMembers(this);
        storage.store("11",new Data());
        s.store("11",new Data());
    }
}
