package com.example.PsiSoftware.messaging;

import java.time.LocalDateTime;

public record EmailMock(String to, String subject, String body, LocalDateTime createdAt) {
}
