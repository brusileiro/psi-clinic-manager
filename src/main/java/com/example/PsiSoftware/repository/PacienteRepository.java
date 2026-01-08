package com.example.PsiSoftware.repository;

import com.example.PsiSoftware.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
