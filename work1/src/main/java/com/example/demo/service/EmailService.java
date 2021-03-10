package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendSimpleMail(String to, String subject, String content);


    public void sendHtmlMail(String to, String subject, String content);
}
