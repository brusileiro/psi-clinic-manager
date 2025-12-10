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
public class PacienteDTO {

    private Long id;
    private String nome;
    private String telefone;
    private String diaHorarioConsulta;
    private BigDecimal valorSessao;
    private BigDecimal saldoAtual;

    public static PacienteDTO from(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setTelefone(paciente.getTelefone());
        dto.setSaldoAtual(paciente.getSaldoAtual());
        dto.setValorSessao(paciente.getValorSessao());
        dto.setDiaHorarioConsulta(paciente.getDiaHorarioConsulta());
        return dto;
    }
}
