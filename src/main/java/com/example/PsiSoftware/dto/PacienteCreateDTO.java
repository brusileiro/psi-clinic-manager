package com.example.PsiSoftware.dto;

import jakarta.validation.constraints.*;
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
public class PacienteCreateDTO {

    @NotBlank
    private String nome;
    @NotBlank
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^\\d+$")
    private String cpf;
    private String telefone;
    @NotBlank
    private String diaHorarioConsulta;
    @NotNull
    @Positive
    private BigDecimal valorSessao;
    private String anotacoes;

    public Paciente toEntity() {
        Paciente paciente = new Paciente();
        paciente.setNome(this.getNome());
        paciente.setCpf(this.getCpf());
        paciente.setTelefone(this.getTelefone());
        paciente.setDiaHorarioConsulta(this.getDiaHorarioConsulta());
        paciente.setValorSessao(this.getValorSessao());
        paciente.setAnotacoes(this.getAnotacoes());
        return paciente;
    }
}
