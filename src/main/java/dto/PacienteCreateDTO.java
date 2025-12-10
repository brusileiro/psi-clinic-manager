package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Paciente;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteCreateDTO {

    private String nome;
    private String cpf;
    private String telefone;
    private String diaHorarioConsulta;
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
