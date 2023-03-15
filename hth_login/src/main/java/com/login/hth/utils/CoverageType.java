package com.login.hth.utils;

public enum CoverageType {
    M("Male"),
    F("Female");

    public final String label;
   private CoverageType(String label)  {
       this.label = label;

    }
}
