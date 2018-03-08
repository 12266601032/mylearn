package com.example.lcc.basic.java.resource.resources;

import java.util.ListResourceBundle;

/**
 * Created by liucongcong on 2018/3/8.
 */
public class BundleByClass_zh_CN extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        //这里是一个二维数组，规范为每个数据里只存2个元素，分别对应 key 、value
        return new Object[][]{
                {"aa","简体中文_zh_CN"}
        };
    }
}
