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
public class PacienteResponseDTO {

    private Long id;
    private String nome;
    private String telefone;
    private String diaHorarioConsulta;
    private BigDecimal valorSessao;
    private BigDecimal saldoAtual;

    public static PacienteResponseDTO from(Paciente paciente) {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setTelefone(paciente.getTelefone());
        dto.setSaldoAtual(paciente.getSaldoAtual());
        dto.setValorSessao(paciente.getValorSessao());
        dto.setDiaHorarioConsulta(paciente.getDiaHorarioConsulta());
        return dto;
    }
}
