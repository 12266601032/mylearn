package com.example.java.resource;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class ResourceLoader {
    public static ResourceBundle bundle = ResourceBundle.getBundle("ResourceBundleDemo",new Locale("zh","CN"));

    public static void main(String[] args) {
        ExceptionUtils.getStackTrace(new RuntimeException());
    }
}
