package com.example.java.resource.resources;

import java.util.ListResourceBundle;

/**
 */
public class BundleByClass_zh_Hans extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"aa","简体中文_zh_Hans"}
        };
    }
}
