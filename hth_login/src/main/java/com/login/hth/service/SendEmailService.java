package com.login.hth.service;

import com.login.hth.utils.ValueOrDefault;
import org.springframework.stereotype.Component;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@Component
public class SendEmailService {
    private Properties props = new Properties();
    private String member;
    private String[] sendTo;
    private String[] sendCC;
    private String[] sendBCC;
    private String sendFrom;
    private String smtpAuth;
    private String ttls;
    private String smtpHost;
    private String smtpPort;
    private String mailDebug;
    private String mailUser;
    private String mailPassword;

    public SendEmailService() throws IOException {
        try {
            String propPath = ValueOrDefault.executeLocation();
            FileReader fileReader = null;
            try{
             fileReader= new FileReader(propPath + "/config/email.properties");
            }catch (Exception ex){
                System.out.println("NO CONFIG FILE");
            }
            if(fileReader!=null)
            props.load(fileReader);
            member = ValueOrDefault.valueOf(props.getProperty("member"), "TRT");
            sendFrom = ValueOrDefault.valueOf(props.getProperty("sendFrom"), "no-reply@hi-techhealth.com");
            sendTo = (ValueOrDefault.valueOf(props.getProperty("sendTo"), "")).split(",");
            sendCC = (ValueOrDefault.valueOf(props.getProperty("sendCC"), "")).split(",");
            sendBCC = (ValueOrDefault.valueOf(props.getProperty("sendBCC"), "")).split(",");
            smtpAuth = ValueOrDefault.valueOf(props.getProperty("smtpAuth"), "true");
            ttls = ValueOrDefault.valueOf(props.getProperty("ttls"), "false");
            smtpHost = ValueOrDefault.valueOf(props.getProperty("smtpHost"), "smtp.socketlabs.com");
            smtpPort = ValueOrDefault.valueOf(props.getProperty("smtpPort"), "587");
            mailDebug = ValueOrDefault.valueOf(props.getProperty("mailDebug"), "false");
            mailUser = ValueOrDefault.valueOf(props.getProperty("mailUsername"), "server33560");
            mailPassword = ValueOrDefault.valueOf(props.getProperty("mailPassword"), "b7FQw36CeZq4a");
        } catch (Exception ex) {
            System.out.println("No Property File Found.");
        }
    }

    public void main(String subject, String content) throws ParseException, IOException {
        try {
            this.sendEmail(subject, content);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public void main(String[] to, String subject, String content) throws ParseException, IOException {
        try {
            this.sendTo = to;
            this.sendEmail(subject, content);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendEmail(String subject, String content) throws MessagingException {
        Properties mailAuth = new Properties();
        mailAuth.put("mail.smtp.host", smtpHost);
        mailAuth.put("mail.smtp.starttls.enable", ttls);
        mailAuth.put("mail.smtp.auth", smtpAuth);
        mailAuth.put("mail.smtp.port", smtpPort);
        mailAuth.put("mail.debug", mailDebug);
        Session session = Session.getInstance(mailAuth, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUser, mailPassword);
            }
        });


        MimeMessage message = new MimeMessage(session);

        InternetAddress[] recipientAddress = new InternetAddress[sendTo.length];
        InternetAddress[] ccAddress = new InternetAddress[sendCC.length];
        InternetAddress[] bccAddress = new InternetAddress[sendBCC.length];
        long ccCount = Arrays.stream(sendCC).filter(s->s.length()>0).count();
        long bccCount = Arrays.stream(sendBCC).filter(s->s.length()>0).count();
        for (int i = 0; i < sendTo.length; i++) {
            recipientAddress[i] = new InternetAddress(sendTo[i].trim());
        }

        if (sendCC!=null && sendCC.length > 0 && ccCount>0) {
            for (int i = 0; i < sendCC.length; i++) {
                if(!sendCC[i].equals(""))
                ccAddress[i] = new InternetAddress(sendCC[i].trim());
            }
            message.setRecipients(Message.RecipientType.CC, ccAddress);
        }

        if (sendCC!=null && sendBCC.length > 0 && bccCount>0) {
            for (int i = 0; i < sendBCC.length; i++) {
                if(!sendBCC[i].equals(""))
                bccAddress[i] = new InternetAddress(sendBCC[i].trim());
            }
            message.setRecipients(Message.RecipientType.BCC, bccAddress);
        }

        message.setFrom(new InternetAddress(sendFrom));
        message.setRecipients(Message.RecipientType.TO, recipientAddress);
        message.setSubject(subject);
        message.setContent(content, "text/html");
        message.setSentDate(new Date());
        Transport.send(message);
    }
}

