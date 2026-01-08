package com.example.PsiSoftware.repository;

import com.example.PsiSoftware.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByPacienteId(Long pacienteId);

}
