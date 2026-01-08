package com.example.PsiSoftware.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.PsiSoftware.model.FormaPagamento;
import com.example.PsiSoftware.model.Paciente;
import com.example.PsiSoftware.model.Pagamento;

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
