package service;

import model.Paciente;
import org.springframework.stereotype.Service;
import repository.PacienteRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {


    private PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente criarPaciente(Paciente paciente) {
        if (paciente.getSaldoAtual() == null) {
            paciente.setSaldoAtual(BigDecimal.ZERO);
        }

        pacienteRepository.save(paciente);
        return paciente;

    }

    public List<Paciente> listarTodos() {

        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId (Long id) {

        return pacienteRepository.findById(id);
    }

    public Paciente atualizarPaciente (Paciente paciente) {
        Optional<Paciente> pacienteAtualizado = buscarPorId(paciente.getId());
        if (pacienteAtualizado.isEmpty()) {
            throw new RuntimeException("Paciente nao encontrado com o ID: " + paciente.getId());
        }
        pacienteRepository.save(paciente);

        return paciente;
    }
}
