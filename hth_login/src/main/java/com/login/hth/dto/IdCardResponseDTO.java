package com.login.hth.dto;

import lombok.Data;

import java.awt.*;

@Data
public class IdCardResponseDTO {
    private String group;
    private String division;
    private String cardNumber;
    private String largeFront;
    private String largeBack;
    //front line upto 18
    private String[] frontLine;
    //back line upto 18
    private String[] backLine;
    //front modifier upto 18
    private String[] frontModifier;
    //back modifier upto 18
    private String[] backModifier;
    //front logo upto 9
    private String[] frontLogo;
    //back logo upto 9
    private String[] backLogo;
    private String[] logos;
//    private Image[] images;


}
