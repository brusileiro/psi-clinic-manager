package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.FormaPagamento;
import model.Paciente;
import model.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoCreateDTO {

    private LocalDate data;
    private BigDecimal valor;
    private FormaPagamento formaPagamento;
    private Long idPaciente;

    public Pagamento toEntity() {
        Pagamento pagamento = new Pagamento();
        pagamento.setData(this.getData());
        pagamento.setFormaPagamento(this.formaPagamento);
        pagamento.setValor(this.getValor());
        Paciente paciente = new Paciente();
        paciente.setId(idPaciente);
        pagamento.setPaciente(paciente);

        return pagamento;
    }
}
