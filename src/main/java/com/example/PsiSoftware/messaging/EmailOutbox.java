package com.example.PsiSoftware.messaging;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailOutbox {

    private final List<EmailMock> sent = new ArrayList<>();

    public void send(EmailMock email) {
        sent.add(email);
    }

    public List<EmailMock> getSent() {
        return new ArrayList<>(sent);
    }

}
