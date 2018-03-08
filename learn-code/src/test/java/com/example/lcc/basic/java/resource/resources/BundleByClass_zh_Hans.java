package com.example.lcc.basic.java.resource.resources;

import java.util.ListResourceBundle;

/**
 * Created by liucongcong on 2018/3/8.
 */
public class BundleByClass_zh_Hans extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"aa","简体中文_zh_Hans"}
        };
    }
}
