package com.example.PsiSoftware.service;

import com.example.PsiSoftware.model.Paciente;
import com.example.PsiSoftware.model.Pagamento;
import com.example.PsiSoftware.repository.PacienteRepository;
import com.example.PsiSoftware.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    // =========================
    // registrarPagamento
    // =========================

    @Test
    void registrarPagamento_deveLancarExcecaoQuandoPacienteForNull() {
        Pagamento pagamento = new Pagamento();
        pagamento.setPaciente(null);

        assertThrows(RuntimeException.class, () -> pagamentoService.registrarPagamento(pagamento));

        verify(pacienteRepository, never()).findById(anyLong());
        verify(pacienteRepository, never()).save(any());
        verify(pagamentoRepository, never()).save(any());
    }

    @Test
    void registrarPagamento_deveLancarExcecaoQuandoPacienteNaoExiste() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);

        Pagamento pagamento = new Pagamento();
        pagamento.setPaciente(paciente);
        pagamento.setValor(new BigDecimal("50.00"));

        when(pacienteRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> pagamentoService.registrarPagamento(pagamento));

        verify(pacienteRepository, times(1)).findById(10L);
        verify(pacienteRepository, never()).save(any());
        verify(pagamentoRepository, never()).save(any());
    }

    @Test
    void registrarPagamento_deveLancarExcecaoQuandoValorForNull() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);
        paciente.setSaldoAtual(new BigDecimal("100.00"));

        Pagamento pagamento = new Pagamento();
        pagamento.setPaciente(paciente);
        pagamento.setValor(null);

        when(pacienteRepository.findById(10L)).thenReturn(Optional.of(paciente));

        assertThrows(RuntimeException.class, () -> pagamentoService.registrarPagamento(pagamento));

        verify(pacienteRepository, never()).save(any());
        verify(pagamentoRepository, never()).save(any());
    }

    @Test
    void registrarPagamento_quandoDataForNull_deveSetarDataHoje_eSubtrairSaldo_eSalvar() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);
        paciente.setSaldoAtual(new BigDecimal("200.00"));

        Pagamento pagamento = new Pagamento();
        pagamento.setPaciente(paciente);
        pagamento.setValor(new BigDecimal("50.00"));
        pagamento.setData(null);

        when(pacienteRepository.findById(10L)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(inv -> inv.getArgument(0));
        when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(inv -> inv.getArgument(0));

        LocalDate antes = LocalDate.now();
        Pagamento resultado = pagamentoService.registrarPagamento(pagamento);
        LocalDate depois = LocalDate.now();

        // Data defaultada (hoje)
        assertNotNull(resultado.getData());
        assertFalse(resultado.getData().isBefore(antes));
        assertFalse(resultado.getData().isAfter(depois));

        // Saldo ajustado: 200 - 50 = 150
        assertEquals(new BigDecimal("150.00"), paciente.getSaldoAtual());

        verify(pacienteRepository, times(1)).save(paciente);
        verify(pagamentoRepository, times(1)).save(pagamento);
    }

    @Test
    void registrarPagamento_quandoDataForInformada_deveManterData_eSubtrairSaldo() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);
        paciente.setSaldoAtual(new BigDecimal("200.00"));

        LocalDate dataInformada = LocalDate.of(2026, 1, 1);

        Pagamento pagamento = new Pagamento();
        pagamento.setPaciente(paciente);
        pagamento.setValor(new BigDecimal("50.00"));
        pagamento.setData(dataInformada);

        when(pacienteRepository.findById(10L)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(inv -> inv.getArgument(0));
        when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(inv -> inv.getArgument(0));

        Pagamento resultado = pagamentoService.registrarPagamento(pagamento);

        assertEquals(dataInformada, resultado.getData());
        assertEquals(new BigDecimal("150.00"), paciente.getSaldoAtual());

        verify(pacienteRepository, times(1)).save(paciente);
        verify(pagamentoRepository, times(1)).save(pagamento);
    }

    // =========================
    // listar/buscar/listarPorPaciente
    // =========================

    @Test
    void listarTodos_deveRetornarListaDoRepository() {
        List<Pagamento> lista = List.of(new Pagamento(), new Pagamento());
        when(pagamentoRepository.findAll()).thenReturn(lista);

        List<Pagamento> resultado = pagamentoService.listarTodos();

        assertSame(lista, resultado);
        verify(pagamentoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_deveRetornarOptionalDoRepository() {
        Pagamento p = new Pagamento();
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Pagamento> resultado = pagamentoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertSame(p, resultado.get());
        verify(pagamentoRepository, times(1)).findById(1L);
    }

    @Test
    void listarPorPaciente_deveChamarFindByPacienteId() {
        List<Pagamento> lista = List.of(new Pagamento());
        when(pagamentoRepository.findByPacienteId(10L)).thenReturn(lista);

        List<Pagamento> resultado = pagamentoService.listarPorPaciente(10L);

        assertSame(lista, resultado);
        verify(pagamentoRepository, times(1)).findByPacienteId(10L);
    }

    // =========================
    // deletarPagamento
    // =========================

    @Test
    void deletarPagamento_quandoNaoExiste_deveLancarExcecao_eNaoDeletar() {
        when(pagamentoRepository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> pagamentoService.deletarPagamento(77L));

        verify(pacienteRepository, never()).save(any());
        verify(pagamentoRepository, never()).delete(any());
    }

    @Test
    void deletarPagamento_quandoExiste_deveSomarSaldo_salvarPaciente_eDeletarPagamento() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);
        paciente.setSaldoAtual(new BigDecimal("100.00"));

        Pagamento pagamento = new Pagamento();
        pagamento.setId(50L);
        pagamento.setPaciente(paciente);
        pagamento.setValor(new BigDecimal("40.00"));

        when(pagamentoRepository.findById(50L)).thenReturn(Optional.of(pagamento));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(inv -> inv.getArgument(0));

        pagamentoService.deletarPagamento(50L);

        // Saldo: 100 + 40 = 140
        assertEquals(new BigDecimal("140.00"), paciente.getSaldoAtual());

        verify(pacienteRepository, times(1)).save(paciente);
        verify(pagamentoRepository, times(1)).delete(pagamento);
    }
}
