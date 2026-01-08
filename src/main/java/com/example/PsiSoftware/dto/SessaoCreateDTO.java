package com.example.PsiSoftware.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.PsiSoftware.model.Paciente;
import com.example.PsiSoftware.model.Sessao;
import com.example.PsiSoftware.model.StatusSessao;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessaoCreateDTO {

    private LocalDate dataSessao;
    private Long idPaciente;
    private BigDecimal valor;
    private StatusSessao status;

    public Sessao toEntity () {
        Sessao sessao = new Sessao();
        sessao.setDataSessao(this.getDataSessao());
        sessao.setValor(this.getValor());
        sessao.setStatus(this.getStatus());
        Paciente paciente = new Paciente();
        paciente.setId(idPaciente);
        sessao.setPaciente(paciente);

        return sessao;
    }
}
