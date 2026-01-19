package com.example.PsiSoftware.service;

import com.example.PsiSoftware.model.Paciente;
import com.example.PsiSoftware.model.Sessao;
import com.example.PsiSoftware.model.StatusSessao;

import com.example.PsiSoftware.repository.PacienteRepository;
import com.example.PsiSoftware.repository.SessaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private SessaoService sessaoService;


    @Test
    void registrarSessao_deveLancarExcecaoQuandoPacienteForNull() {
        Sessao sessao = new Sessao();
        sessao.setPaciente(null);

        assertThrows(RuntimeException.class, () -> sessaoService.registrarSessao(sessao));

        verify(pacienteRepository, never()).save(any());
        verify(sessaoRepository, never()).save(any());
        verify(pacienteRepository, never()).findById(anyLong());
    }

    @Test
    void registrarSessao_quandoValorForNull_deveUsarValorSessaoDoPaciente_eSomarNoSaldo_quandoStatusRealizada() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);
        paciente.setSaldoAtual(BigDecimal.ZERO);
        paciente.setValorSessao(new BigDecimal("100.00"));

        Sessao sessao = new Sessao();
        sessao.setPaciente(paciente); // vem com id
        sessao.setValor(null);
        sessao.setStatus(StatusSessao.REALIZADA);

        when(pacienteRepository.findById(10L)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(inv -> inv.getArgument(0));
        when(sessaoRepository.save(any(Sessao.class))).thenAnswer(inv -> inv.getArgument(0));

        Sessao resultado = sessaoService.registrarSessao(sessao);

        assertEquals(new BigDecimal("100.00"), resultado.getValor());
        assertEquals(new BigDecimal("100.00"), paciente.getSaldoAtual());

        verify(pacienteRepository, times(1)).findById(10L);
        verify(pacienteRepository, times(1)).save(paciente);
        verify(sessaoRepository, times(1)).save(sessao);
    }

    @Test
    void registrarSessao_quandoStatusForNull_naoDeveAlterarSaldo_masDeveSalvar() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);
        paciente.setSaldoAtual(new BigDecimal("50.00"));
        paciente.setValorSessao(new BigDecimal("100.00"));

        Sessao sessao = new Sessao();
        sessao.setPaciente(paciente);
        sessao.setStatus(null);
        sessao.setValor(null);

        when(pacienteRepository.findById(10L)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(inv -> inv.getArgument(0));
        when(sessaoRepository.save(any(Sessao.class))).thenAnswer(inv -> inv.getArgument(0));

        sessaoService.registrarSessao(sessao);

        assertEquals(new BigDecimal("100.00"), sessao.getValor());
        assertEquals(new BigDecimal("50.00"), paciente.getSaldoAtual());

        verify(pacienteRepository, times(1)).save(paciente);
        verify(sessaoRepository, times(1)).save(sessao);
    }

    @Test
    void registrarSessao_quandoStatusNaoForNull_eValorContinuarNull_deveLancarExcecao() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);
        paciente.setSaldoAtual(BigDecimal.ZERO);
        paciente.setValorSessao(null);

        Sessao sessao = new Sessao();
        sessao.setPaciente(paciente);
        sessao.setValor(null);
        sessao.setStatus(StatusSessao.REALIZADA);

        when(pacienteRepository.findById(10L)).thenReturn(Optional.of(paciente));

        assertThrows(RuntimeException.class, () -> sessaoService.registrarSessao(sessao));

        verify(pacienteRepository, never()).save(any());
        verify(sessaoRepository, never()).save(any());
    }

    @Test
    void registrarSessao_quandoPacienteNaoExisteNoRepository_deveLancarExcecao() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);

        Sessao sessao = new Sessao();
        sessao.setPaciente(paciente);

        when(pacienteRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> sessaoService.registrarSessao(sessao));

        verify(pacienteRepository, times(1)).findById(10L);
        verify(pacienteRepository, never()).save(any());
        verify(sessaoRepository, never()).save(any());
    }

    @Test
    void listarTodas_deveRetornarListaDoRepository() {
        List<Sessao> lista = List.of(new Sessao(), new Sessao());
        when(sessaoRepository.findAll()).thenReturn(lista);

        List<Sessao> resultado = sessaoService.listarTodas();

        assertSame(lista, resultado);
        verify(sessaoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_deveRetornarOptionalDoRepository() {
        Sessao s = new Sessao();
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(s));

        Optional<Sessao> resultado = sessaoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertSame(s, resultado.get());
        verify(sessaoRepository, times(1)).findById(1L);
    }

    @Test
    void listarPorPaciente_deveChamarFindByPacienteId() {
        List<Sessao> lista = List.of(new Sessao());
        when(sessaoRepository.findByPacienteId(10L)).thenReturn(lista);

        List<Sessao> resultado = sessaoService.listarPorPaciente(10L);

        assertSame(lista, resultado);
        verify(sessaoRepository, times(1)).findByPacienteId(10L);
    }

    @Test
    void atualizarSessao_quandoNaoExiste_deveLancarExcecao_eNaoSalvar() {
        Sessao entrada = new Sessao();
        entrada.setId(99L);

        when(sessaoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> sessaoService.atualizarSessao(entrada));

        verify(pacienteRepository, never()).save(any());
        verify(sessaoRepository, never()).save(any());
    }

    @Test
    void atualizarSessao_quandoExiste_deveReverterSaldo_antigo_eAplicarNovoESalvar() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);
        paciente.setSaldoAtual(new BigDecimal("200.00"));

        Sessao existente = new Sessao();
        existente.setId(50L);
        existente.setPaciente(paciente);
        existente.setStatus(StatusSessao.REALIZADA);
        existente.setValor(new BigDecimal("100.00"));

        Sessao entrada = new Sessao();
        entrada.setId(50L);
        entrada.setStatus(StatusSessao.REALIZADA);
        entrada.setValor(new BigDecimal("50.00"));
        entrada.setDataSessao(existente.getDataSessao());

        when(sessaoRepository.findById(50L)).thenReturn(Optional.of(existente));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(inv -> inv.getArgument(0));
        when(sessaoRepository.save(any(Sessao.class))).thenAnswer(inv -> inv.getArgument(0));

        Sessao resultado = sessaoService.atualizarSessao(entrada);

        assertEquals(new BigDecimal("150.00"), paciente.getSaldoAtual());

        assertSame(existente, resultado);

        verify(pacienteRepository, times(1)).save(paciente);
        verify(sessaoRepository, times(1)).save(existente);
    }

    @Test
    void deletarSessao_quandoNaoExiste_deveLancarExcecao_eNaoDeletar() {
        when(sessaoRepository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> sessaoService.deletarSessao(77L));

        verify(pacienteRepository, never()).save(any());
        verify(sessaoRepository, never()).delete(any());
    }

    @Test
    void deletarSessao_quandoExiste_deveReverterSaldo_salvarPaciente_eDeletarSessao() {
        Paciente paciente = new Paciente();
        paciente.setId(10L);
        paciente.setSaldoAtual(new BigDecimal("200.00"));

        Sessao existente = new Sessao();
        existente.setId(50L);
        existente.setPaciente(paciente);
        existente.setStatus(StatusSessao.REALIZADA);
        existente.setValor(new BigDecimal("100.00"));

        when(sessaoRepository.findById(50L)).thenReturn(Optional.of(existente));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(inv -> inv.getArgument(0));

        boolean resultado = sessaoService.deletarSessao(50L);

        assertTrue(resultado);
        assertEquals(new BigDecimal("100.00"), paciente.getSaldoAtual());

        verify(pacienteRepository, times(1)).save(paciente);
        verify(sessaoRepository, times(1)).delete(existente);
    }
}
