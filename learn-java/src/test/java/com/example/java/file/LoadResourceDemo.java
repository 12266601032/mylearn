package com.example.java.file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Properties;

/**
 */
public class LoadResourceDemo {

    @Test
    public void loadFile() throws Exception {
        /*
         * 方式一：
         * 通过Class对象读取资源，会经过resolveName(name)来转换文件名
         *
         * 如果不是以 / 开头 会自动转换为com/example/lcc/basic/java/field/LoadResourceDemo
         * 因此这种方式读取资源时相对路径是当前类的包下
         * 因此 使用 / 是项目下的绝对路径
         */
        try (InputStream is = this.getClass().getResourceAsStream("/test.properties")) {
            Properties p1 = new Properties();
            p1.load(is);
            System.out.println(p1.get("aa"));
            HashMap<Object, Object> map = new HashMap<>(p1);
            System.out.println(map.get("aa"));

        }

        /*
         * 方式二：
         *  通过classLoader加载，项目跟路径为相对路径
         */
        try (InputStream is2 = this.getClass().getClassLoader().getResourceAsStream("test.properties")) {
            Properties p2 = new Properties();
            p2.load(is2);
            System.out.println(p2.get("aa"));
        }

        //通过getResource获取对应的File
        URL resource = this.getClass().getResource("/test.properties");
        File file = new File(resource.toURI());
        System.out.println(file.getName());
    }
}
