package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Paciente;
import model.Sessao;
import model.StatusSessao;

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
