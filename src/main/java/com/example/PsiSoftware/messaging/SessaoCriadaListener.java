package com.example.PsiSoftware.messaging;

import com.example.PsiSoftware.event.SessaoCriadaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SessaoCriadaListener {

    private final EmailOutbox emailOutbox;

    @Async
    @EventListener
    public void onSessaoCriada(SessaoCriadaEvent event){;

    EmailMock email = new EmailMock(
            "mock@local",
            "Recibo - Sessão",
            "Recibo da sessão\nPaciente: " + event.pacienteNome()
                    + "\nData: " + event.dataSessao(),
            LocalDateTime.now()
    );

        emailOutbox.send(email);
}
}