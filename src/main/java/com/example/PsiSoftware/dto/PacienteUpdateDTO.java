package com.example.PsiSoftware.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.PsiSoftware.model.Paciente;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteUpdateDTO {

    private String nome;
    private String telefone;
    private String diaHorarioConsulta;
    private BigDecimal valorSessao;
    private String anotacoes;

    public void aplicarAtualizacoes(Paciente paciente) {
        if (this.getNome() != null) {
        paciente.setNome(this.getNome());
        }
        if (this.getTelefone() != null) {
            paciente.setTelefone(this.getTelefone());
        }
        if (this.getDiaHorarioConsulta() != null) {
            paciente.setDiaHorarioConsulta(this.getDiaHorarioConsulta());
        }
        if (this.getValorSessao() != null) {
            paciente.setValorSessao(this.getValorSessao());
        }
        if (this.getAnotacoes() != null) {
            paciente.setAnotacoes(this.getAnotacoes());
        }
    }
}
