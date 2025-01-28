package org.dtna.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NotificationMail {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(){
        List<String> senderEmails=new ArrayList<>();
        senderEmails.add("shankara.dhanakodi@tavant.com");
        senderEmails.add("anik.sinha@tavant.com");

        senderEmails.add("anik.sinha@tavant.com");
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("aniksinha1999@gmail.com");
        simpleMailMessage.setTo(new String[] {"shankara.dhanakodi@tavant.com", "anik.sinha@tavant.com"});
        simpleMailMessage.setText("Hi this is sample mail");
        javaMailSender.send(simpleMailMessage);
    }
}
