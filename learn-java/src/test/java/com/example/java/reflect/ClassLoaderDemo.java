package com.example.java.reflect;

import org.junit.Test;

import java.net.URL;
import java.util.Enumeration;

/**
 * @date 2018/4/9
 */
public class ClassLoaderDemo {

    @Test
    public void loadResource() throws Exception {
        Enumeration<URL> urls = ClassLoader.getSystemResources("META-INF/spring.factories");
        printEnumeration(urls);
        urls = this.getClass().getClassLoader().getResources("META-INF/spring.factories");
        printEnumeration(urls);
    }

    private void printEnumeration(Enumeration<URL> urls) {
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println(url);
        }
    }
}
