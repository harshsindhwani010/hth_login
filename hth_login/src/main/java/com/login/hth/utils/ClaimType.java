package com.login.hth.utils;

public enum ClaimType {
    H("Medical"),
    M("Medical"),
    V("Vision"),
    D("Dental");




    public final String label;

    private ClaimType(String label){
        this.label=label;
    }

}
