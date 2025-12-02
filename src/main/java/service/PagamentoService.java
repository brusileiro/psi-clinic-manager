package service;

import model.Paciente;
import model.Pagamento;
import repository.PacienteRepository;
import repository.PagamentoRepository;
import repository.SessaoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PagamentoService {

    private PacienteRepository pacienteRepository;
    private SessaoRepository sessaoRepository;
    private PagamentoRepository pagamentoRepository;


    public Pagamento registrarPagamento(Pagamento pagamento) {
        if (pagamento.getPaciente() == null) {
            throw new RuntimeException();
        }

        Optional<Paciente> optinal = pacienteRepository.findById(pagamento.getPaciente().getId());
        Paciente paciente = optinal.orElseThrow();

        if (pagamento.getValor() == null) {
            throw new RuntimeException();
        }
        if (pagamento.getData() == null) {
            pagamento.setData(LocalDate.now());
        }
        paciente.setSaldoAtual(paciente.getSaldoAtual().subtract(pagamento.getValor()));
        pacienteRepository.save(paciente);
        pagamentoRepository.save(pagamento);

        return pagamento;
    }

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public Optional<Pagamento> buscarPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    public void deletarPagamento (Long id) {
        Optional<Pagamento> pagamentoOptinal = pagamentoRepository.findById(id);
        Pagamento pagamento = pagamentoOptinal.orElseThrow();

        Paciente paciente = pagamento.getPaciente();

        paciente.setSaldoAtual(paciente.getSaldoAtual().add(pagamento.getValor()));
        pacienteRepository.save(paciente);
        pagamentoRepository.delete(pagamento);

    }
}
