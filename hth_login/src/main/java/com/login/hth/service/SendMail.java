package com.login.hth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {

  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.username}") String sender;

  public void sendingMail(String to, String subject, String body) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(sender);
    mailMessage.setTo(to);
    mailMessage.setText(body);
    mailMessage.setSubject(subject);
    javaMailSender.send(mailMessage);
  }}