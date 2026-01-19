package com.example.PsiSoftware.event;

import java.time.LocalDateTime;

public record SessaoCriadaEvent(String pacienteNome, LocalDateTime dataSessao) {
}
