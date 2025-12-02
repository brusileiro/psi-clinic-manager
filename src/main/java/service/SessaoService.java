package service;

import model.Paciente;
import model.Sessao;
import org.springframework.stereotype.Service;
import repository.PacienteRepository;
import repository.SessaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {

    private PacienteRepository pacienteRepository;
    private SessaoRepository sessaoRepository;

    public SessaoService(SessaoRepository sessaoRepository, PacienteRepository pacienteRepository) {
        this.sessaoRepository = sessaoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public Sessao registrarSessao (Sessao sessao) {
        if (sessao.getPaciente() == null) {
            throw new RuntimeException();
        }

        Optional<Paciente> pacienteOptinal = pacienteRepository.findById(sessao.getPaciente().getId());
        Paciente paciente = pacienteOptinal.orElseThrow();

        if (sessao.getValor() == null) {
            sessao.setValor(paciente.getValorSessao());
        }

        ajustarSaldo(paciente, sessao);

        pacienteRepository.save(paciente);
        sessaoRepository.save(sessao);

        return sessao;

    }

    public List<Sessao> listarTodas() {
        return sessaoRepository.findAll();
    }

    public Optional<Sessao> buscarPorId (Long id) {
        return sessaoRepository.findById(id);
    }

    public Sessao atualizarSessao(Sessao sessao) {
        Optional<Sessao> optional = sessaoRepository.findById(sessao.getId());

       Sessao sessaoEncontrada = optional.orElseThrow();
        Paciente paciente = sessaoEncontrada.getPaciente();
        reverterSaldo(paciente, sessaoEncontrada);

        sessaoEncontrada.setDataSessao(sessao.getDataSessao());
        sessaoEncontrada.setValor(sessao.getValor());
        sessaoEncontrada.setStatus(sessao.getStatus());

        ajustarSaldo(paciente, sessaoEncontrada);
        pacienteRepository.save(paciente);
        sessaoRepository.save(sessaoEncontrada);

        return sessaoEncontrada;
    }

    public boolean deletarSessao (Long id) {
        Optional<Sessao> optional = sessaoRepository.findById(id);

        Sessao sessaoEncontrada = optional.orElseThrow();
        Paciente paciente = sessaoEncontrada.getPaciente();
        reverterSaldo(paciente, sessaoEncontrada);

        pacienteRepository.save(paciente);
        sessaoRepository.delete(sessaoEncontrada);

        return true;
    }

    private void reverterSaldo(Paciente paciente, Sessao sessao) {

        if (sessao.getStatus() == null) {
            return;
        }
        if (sessao.getValor() == null) {
            throw new RuntimeException();
        }
        switch (sessao.getStatus()) {
            case REALIZADA:
                paciente.setSaldoAtual(paciente.getSaldoAtual().subtract(sessao.getValor()));
                break;

            case FALTA_COBRADA:
                paciente.setSaldoAtual(paciente.getSaldoAtual().subtract(sessao.getValor()));
                break;

            case FALTA_NAO_COBRADA:
                break;
        }
    }

    private void ajustarSaldo (Paciente paciente, Sessao sessao) {

        if (sessao.getStatus() == null) {
            return;
        }
        if (sessao.getValor() == null) {
            throw new RuntimeException();
        }
        switch (sessao.getStatus()) {
            case REALIZADA :
                paciente.setSaldoAtual(paciente.getSaldoAtual().add(sessao.getValor()));
                break;

            case FALTA_COBRADA:
                paciente.setSaldoAtual(paciente.getSaldoAtual().add(sessao.getValor()));
                break;

            case FALTA_NAO_COBRADA:
                break;

        }
    }
}
