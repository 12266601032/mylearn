package com.example.lcc.basic.java.resource;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * BundleByClass 可以轻松实现国际化
 * 命名规则按照：资源名_语言_国别.properties
 * <p>
 * baseName + "_" + language + "_" + script + "_" + country + "_" + variant
 * For example,
 * if baseName is "baseName" and locale is Locale("ja", " ", "XX"),then "baseName_ja_ _XX" is returned.
 * If the given locale is Locale("en"), then "baseName_en" is returned.
 * 如果后面的为空 则会连"_"一起省略掉
 *
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
         * 比如这个会有
         * 0 = {Locale@534} "zh_CN_#Hans"           baseName_zh_Hans_CN.properties
         * 1 = {Locale@535} "zh__#Hans"             baseName_zh_Hans.properties
         * 2 = {Locale@521} "zh_CN"                 baseName_zh_CN.properties
         * 3 = {Locale@536} "zh"                    baseName_zh.properties
         * 4 = {Locale@537} ""                      baseName.properties
         * 然后在解析ResourceBundle会根据上面的次序 从 4到1 一次加载，并将加载到的bundle设置好子父级关系
         * 在执行resourceBundle.getString("aa")时，如果在当前bundle找不到 会向上依次查询。
         * 如果在properties中与上面的对应的properties都配置了，会优先从 baseName_zh_Hans_CN.properties
         * 中获取配置，然后依次向上查找。
         *
         * 二、使用class
         * 在命名规范上，与上面的都一致 不过都是java文件
         * 如果class方式与properties共存，会优先加载class方式的。
         *
         *
         */
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ResourceBundleDemo",
                new Locale("zh","CN"));
        System.out.println(resourceBundle.getString("aa"));
        ResourceBundle byClass = ResourceBundle.getBundle(
                "com.example.lcc.basic.java.resource.resources.BundleByClass",
                new Locale("zh","CN"));
        System.out.println(byClass.getString("aa"));
    }

}
