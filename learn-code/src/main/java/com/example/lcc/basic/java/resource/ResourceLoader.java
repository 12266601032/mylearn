package com.example.lcc.basic.java.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceLoader {
    public static ResourceBundle bundle = ResourceBundle.getBundle("ResourceBundleDemo",new Locale("zh","CN"));
}
