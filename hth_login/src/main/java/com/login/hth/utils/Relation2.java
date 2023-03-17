package com.login.hth.utils;

import java.util.HashMap;
import java.util.Map;

public class Relation2 {
    public static Map<String, String> mapper = new HashMap<>();

    static {
        mapper.put("EE","Employee");
        mapper.put("SP","Spouse");
        mapper.put("CH","Child");
        mapper.put("01"," Child");
    }
}
