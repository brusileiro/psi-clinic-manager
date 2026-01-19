package com.example.PsiSoftware.event;

import java.time.LocalDate;

public record SessaoCriadaEvent(String pacienteNome, LocalDate dataSessao) {
}
