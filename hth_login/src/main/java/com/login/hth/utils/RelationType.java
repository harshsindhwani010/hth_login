package com.login.hth.utils;

public enum RelationType {
    EE("Employee"),
    SP("Spouse"),
    CH("Child");

    public final String label;
    private RelationType(String label){
        this.label = label;
    }

}
