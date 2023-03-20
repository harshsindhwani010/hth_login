package com.login.hth.utils;

import java.util.HashMap;
import java.util.Map;

public class ClaimType2 {

    public static Map<String, String> mapper = new HashMap<>();

    static {
      mapper.put("A","HRA");
      mapper.put("F","FSA");
      mapper.put("G","FSA");
      mapper.put("H","Medical");
      mapper.put("M","Medical");
      mapper.put("V","Vision");
      mapper.put("D","Dental");
      mapper.put("R","Prescription");
      mapper.put("W","Disability");

    }

}
