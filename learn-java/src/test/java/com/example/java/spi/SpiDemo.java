package com.example.java.spi;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * service provider interface
 * <p>
 * ServiceLoader.load(interface)
 * 是从javv6提供的一组api 来实现像jdbc一样 由各个服务者提供实现向jdbc的DriverManager中
 * 注册服务实现类对象，从而使api有一个很灵活的拓展点。
 */
public class SpiDemo {

    public static class ConcreteAAA implements ServiceAPI {
        @Override
        public void service() {
            System.out.println(this.getClass() + "servicing...");
        }
    }

    public static interface ServiceAPI {
        void service();
    }

    public static class ConcretePPP implements ServiceAPI {
        @Override
        public void service() {
            System.out.println(this.getClass() + "servicing...");
        }
    }

    public static class ConcreteIII implements ServiceAPI {
        @Override
        public void service() {
            System.out.println(this.getClass() + "servicing...");
        }
    }

    @Test
    public void serviceLoader() throws ClassNotFoundException {
        /**
         * 使用ServiceLoader的话，API的实现类要提供一个无参构造，给loader实例化用
         * 在资源路径下创建文件
         * META-INF/services/com.example.CodecSet
         *
         * 在com.example.CodecSet中配置上实现类的全路径
         * 本例的话就是
         * META-INF/services/SpiDemo$ServiceAPI
         * 在com.example.lcc.basic.java.spi.SpiDemo.ServiceAPI中加入com.example.lcc.basic.java.spi.SpiDemo$ConcreteAAA
         *
         * 如果有多个可以直接都写入
         * SpiDemo$ConcreteAAA
         * SpiDemo$ConcretePPP
         * SpiDemo$ConcreteIII
         */
        Class.forName("com.example.lcc.basic.java.spi.SpiDemo$ConcreteAAA");
        System.out.println(ServiceAPI.class);
        ServiceLoader<ServiceAPI> apis = ServiceLoader.load(ServiceAPI.class);
        Iterator<ServiceAPI> apiIterator = apis.iterator();
        while (apiIterator.hasNext()) {
            ServiceAPI api = apiIterator.next();
            api.service();
        }
    }


}
