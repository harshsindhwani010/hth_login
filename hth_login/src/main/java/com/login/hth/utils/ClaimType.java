package com.login.hth.utils;

public enum ClaimType {
    A("HRA"),
    F("FSA"),
    G("FSA"),
    H("Medical"),
    M("Medical"),
    V("Vision"),
    D("Dental"),
    R("Prescription "),
    W("Disability ");

    public final String label;

    private ClaimType(String label){
        this.label=label;
    }

}
