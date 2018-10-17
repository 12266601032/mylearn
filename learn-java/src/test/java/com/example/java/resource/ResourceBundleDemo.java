package com.example.java.resource;

import org.junit.Test;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ResourceBundle 可以轻松实现国际化
 * 命名规则按照：资源名_语言_国别.properties
 * <p>
 * baseName + "_" + language + "_" + script + "_" + country + "_" + variant
 * For example,
 * if baseName is "baseName" and locale is Locale("ja", "", "XX"),then "baseName_ja__XX" is returned.
 * If the given locale is Locale("en"), then "baseName_en" is returned.
 * 如果后面的为空 则会连"_"一起省略掉
 */
public class ResourceBundleDemo {


    @Test
    public void resourceBundle() {

        /**
         * resource bundle 有两种方式：
         * 一、使用properties
         * 命名为
         * baseName + "_" + language + "_" + script + "_" + country + "_" + variant
         * 例如：
         * new Locale("zh","CN")
         * 则对应的resource为
         * baseName_zh_CN.properties
         *
         * 在加载resource时 会根据当前的Locale解析出一堆相关的候选Local
         * java.util.BundleByClass.Control#getCandidateLocales(java.lang.String, java.util.Locale)
         * 比如这个就会有
         * 0 = {Locale@534} "zh_CN_#Hans"           baseName_zh_Hans_CN.properties
         * 1 = {Locale@535} "zh__#Hans"             baseName_zh_Hans.properties
         * 2 = {Locale@521} "zh_CN"                 baseName_zh_CN.properties
         * 3 = {Locale@536} "zh"                    baseName_zh.properties
         * 4 = {Locale@537} ""                      baseName.properties
         * 然后在解析ResourceBundle会根据上面的次序 从 4到1 依次加载，并将加载到的bundle设置好子父级关系
         * 在执行resourceBundle.getString("aa")时，如果在当前bundle找不到 会向上依次查询。
         * 如果在properties中与上面的对应的properties都配置了，会优先从 baseName_zh_Hans_CN.properties
         * 中获取配置，然后依次向上查找。
         *
         * 二、使用class
         * 在命名规范上，与上面的都一致 不过都是java文件
         * 如果class方式与properties共存，会优先加载class方式的。
         * 由于这种方式去实例化是通过classLoader.loadClass("com.example.ResourceXXX");
         * 来加载到目标类的Class对象然后利用class的newInstance()来创建实例
         * 需要注意的是：
         * 1、这个class要继承自ResourceBundle 通常是继承ListResourceBundle
         * 2、这个class要有可以被访问的无参构造
         *
         *
         * 如果在打好的jar包中去替换已经存在的resource
         * 是properties的话 在classpath下的同级目录 创建一样的resource.properties即可
         * 是class的话 创建同样的包名、类名 即可完成覆盖（这里测试覆盖rt.jar中的class形式的resource时失败掉了，考虑是由于
         * java类加载机制是先委托父加载器来加载类，这样rt.jar的一些包 是不能被替换的。不过第三方的jar是没问题的。而且这个也通常
         * 没必要这么做。）
         *
         */
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ResourceBundleDemo",
                new Locale("zh", "CN"));
        System.out.println(resourceBundle.getString("aa"));
        ResourceBundle byClass = ResourceBundle.getBundle(
                "BundleByClass",
                new Locale("zh", "CN"));
        System.out.println(byClass.getString("aa"));
        System.out.println(new Date());
    }

    @Test
    public void testOverride() {
        String aa = ResourceLoader.bundle.getString("aa");
        System.out.println(aa);
    }


}
