package com.example.lcc.basic.java.resource.resources;

import java.util.ListResourceBundle;

/**
 */
public class BundleByClass_zh extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"aa","简体中文zh"}
        };
    }
}
