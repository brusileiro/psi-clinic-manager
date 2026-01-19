package com.example.PsiSoftware.messaging;

import com.example.PsiSoftware.event.SessaoCriadaEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessaoCriadaListenerTest {

    @Test
    void onSessaoCriada_deveEnviarEmailMockParaOutbox() {
        EmailOutbox outbox = new EmailOutbox();
        SessaoCriadaListener listener = new SessaoCriadaListener(outbox);

        String nome = "Bruno";
        LocalDateTime dataSessao = LocalDateTime.of(2026, 1, 19, 16, 0);
        SessaoCriadaEvent event = new SessaoCriadaEvent(nome, dataSessao);

        listener.onSessaoCriada(event);

        List<EmailMock> enviados = outbox.getSent();
        assertEquals(1, enviados.size());

        EmailMock email = enviados.get(0);

        assertNotNull(email.to());
        assertNotNull(email.subject());
        assertNotNull(email.body());
        assertNotNull(email.createdAt());

        assertTrue(email.body().contains(nome));
        assertTrue(email.body().contains("2026"));
    }
}
