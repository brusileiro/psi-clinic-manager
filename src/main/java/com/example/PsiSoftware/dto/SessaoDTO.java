package com.example.PsiSoftware.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.PsiSoftware.model.Sessao;
import com.example.PsiSoftware.model.StatusSessao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessaoDTO {

    private Long id;
    private LocalDateTime dataSessao;
    private String paciente;
    private BigDecimal valor;
    private StatusSessao status;

    public static SessaoDTO from(Sessao sessao) {
        SessaoDTO dto = new SessaoDTO();
        dto.setId(sessao.getId());
        dto.setDataSessao(sessao.getDataSessao());
        dto.setPaciente(sessao.getPaciente().getNome());
        dto.setValor(sessao.getValor());
        dto.setStatus(sessao.getStatus());
        return dto;
    }
}
