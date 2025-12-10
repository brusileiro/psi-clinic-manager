package repository;

import model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

List<Sessao> findByPacienteId(Long pacienteId);
}
