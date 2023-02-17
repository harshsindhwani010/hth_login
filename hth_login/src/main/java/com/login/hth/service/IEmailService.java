package com.login.hth.service;

public interface IEmailService {
    String sendSimpleMail(String to,String subject,String content);
}
