package com.login.hth.utils;

import java.util.Optional;

public class ValueOrDefault {

    public static String valueOf(String property, String defaultValue){
        return Optional.ofNullable(property).orElse(defaultValue);
    }

    public static String executeLocation(){
        String propPath = ValueOrDefault.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        propPath = propPath.substring(0, propPath.lastIndexOf("/"));
        return propPath;
    }
}
