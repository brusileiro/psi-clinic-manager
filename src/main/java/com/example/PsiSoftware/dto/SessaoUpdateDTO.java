package com.example.PsiSoftware.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.PsiSoftware.model.Sessao;
import com.example.PsiSoftware.model.StatusSessao;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SessaoUpdateDTO {
    private BigDecimal valor;
    private StatusSessao status;
    private LocalDate dataSessao;

    public void aplicarAtualizacoes (Sessao sessao) {
        if (this.getValor() != null) {
            sessao.setValor(this.getValor());
        }
        if (this.getStatus() != null) {
            sessao.setStatus(this.getStatus());
        }
        if (this.getDataSessao() != null) {
            sessao.setDataSessao(this.getDataSessao());
        }
    }
}
