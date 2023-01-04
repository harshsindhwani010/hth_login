package com.login.hth.exception;

public class UserNotFoundException extends RuntimeException{

    UserNotFoundException(){

    }
    UserNotFoundException(String msg){
        super(msg);
    }

    UserNotFoundException(Exception ex){
        super(ex);
    }
}
