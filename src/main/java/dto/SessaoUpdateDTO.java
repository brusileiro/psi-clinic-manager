package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Sessao;
import model.StatusSessao;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SessaoUpdateDTO {
    private BigDecimal valor;
    private StatusSessao status;
    private LocalDate dataSessao;

    public void aplicarAtualizacoes (Sessao sessao) {
        if (this.getValor() != null) {
            sessao.setValor(this.getValor());
        }
        if (this.getStatus() != null) {
            sessao.setStatus(this.getStatus());
        }
        if (this.getDataSessao() != null) {
            sessao.setDataSessao(this.getDataSessao());
        }
    }
}
