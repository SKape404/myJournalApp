package com.prabhav.myJournalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendMailTest() {
        emailService.sendEmail("slimshady12101999@gmail.com", "Hello", "Hello");
    }
}
