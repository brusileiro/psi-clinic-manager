package service;

import lombok.RequiredArgsConstructor;
import model.Paciente;
import org.springframework.stereotype.Service;
import repository.PacienteRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PacienteService {


    private final PacienteRepository pacienteRepository;


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
        Optional<Paciente> optional = buscarPorId(paciente.getId());
        if (optional.isEmpty()) {
            throw new RuntimeException("Paciente nao encontrado com o ID: " + paciente.getId());
        }

        Paciente pacienteAtualizado = optional.get();

        pacienteAtualizado.setNome(paciente.getNome());
        pacienteAtualizado.setCpf(paciente.getCpf());
        pacienteAtualizado.setAnotacoes(paciente.getAnotacoes());
        pacienteAtualizado.setTelefone(paciente.getTelefone());
        pacienteAtualizado.setDiaHorarioConsulta(paciente.getDiaHorarioConsulta());
        pacienteAtualizado.setValorSessao(paciente.getValorSessao());


        pacienteRepository.save(pacienteAtualizado);

        return pacienteAtualizado;
    }

    public void deletarPaciente (Long id) {
        Optional<Paciente> optional = pacienteRepository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("Paciente nao encontrado");
        }
        Paciente pacienteEncontrado = optional.get();

        pacienteRepository.delete(pacienteEncontrado);
    }
}
