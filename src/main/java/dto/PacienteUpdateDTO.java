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
