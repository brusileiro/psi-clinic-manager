package com.example.PsiSoftware.service;

import com.example.PsiSoftware.model.Paciente;
import com.example.PsiSoftware.model.Pagamento;
import com.example.PsiSoftware.repository.PacienteRepository;
import com.example.PsiSoftware.repository.PagamentoRepository;
import com.example.PsiSoftware.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
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

    public List<Pagamento> listarPorPaciente(Long pacienteId) {
        return pagamentoRepository.findByPacienteId(pacienteId);
    }


}
