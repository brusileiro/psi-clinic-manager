package com.example.PsiSoftware.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.PsiSoftware.model.FormaPagamento;
import com.example.PsiSoftware.model.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {

    private Long id;
    private LocalDate data;
    private BigDecimal valor;
    private FormaPagamento formaPagamento;
    private String paciente;

    public static PagamentoDTO from(Pagamento pagamento) {
        PagamentoDTO dto = new PagamentoDTO();
        dto.setId(pagamento.getId());
        dto.setData(pagamento.getData());
        dto.setValor(pagamento.getValor());
        dto.setFormaPagamento(pagamento.getFormaPagamento());
        dto.setPaciente(pagamento.getPaciente().getNome());
        return dto;
    }
}
