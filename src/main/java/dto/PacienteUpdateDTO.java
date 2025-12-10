package dto;

import controller.PacienteController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Paciente;
import service.PacienteService;

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
        paciente.setNome(this.getNome());
        paciente.setTelefone(this.getTelefone());
        paciente.setDiaHorarioConsulta(this.getDiaHorarioConsulta());
        paciente.setValorSessao(this.getValorSessao());
        paciente.setAnotacoes(this.getAnotacoes());
    }
}
