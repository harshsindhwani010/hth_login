package com.login.hth.utils;

import java.util.HashMap;
import java.util.Map;

public class CoverageType {

    public static Map<String, String> mapper = new HashMap<>();

    static {

        mapper.put("M","Medical");
        mapper.put("D","Dental");
        mapper.put("V","Vision");
    }


    }

