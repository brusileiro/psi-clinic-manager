package com.example.PsiSoftware.service;

import com.example.PsiSoftware.model.Paciente;
import com.example.PsiSoftware.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    PacienteRepository pacienteRepository;
    @InjectMocks
    PacienteService pacienteService;

    @Test
    void DeveFalharCPFCurto() {
        Paciente paciente = new Paciente();
        paciente.setCpf("123");

        assertThrows(RuntimeException.class, () -> pacienteService.criarPaciente(paciente));

        verify(pacienteRepository, never()).save(any());
    }

    @Test
    void DeveFalharCPFLongo() {
        Paciente paciente = new Paciente();
        paciente.setCpf("12345678901234");

        assertThrows(RuntimeException.class, () -> pacienteService.criarPaciente(paciente));

        verify(pacienteRepository, never()).save(any());
    }

    @Test
    void deveCriarPacienteComCpfValidoESaldoNullViraZero() {
        Paciente paciente = new Paciente();
        paciente.setCpf("45290053808");
        Paciente pacienteSalvo = new Paciente();
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteSalvo);
        Paciente resultado = pacienteService.criarPaciente(paciente);
        assertSame(pacienteSalvo, resultado);
        assertEquals(paciente.getSaldoAtual(), BigDecimal.ZERO);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void deveListarTodosRetornandoListaDoRepository() {

    }

    @Test
    void deveBuscarPorIdQuandoExiste() {

    }

    @Test
    void deveLancarExcecaoAoBuscarPorIdQuandoNaoExiste() {

    }

    @Test
    void deveAtualizarQuandoIdExiste() {

            Paciente entrada = new Paciente();
            entrada.setId(10L);
            entrada.setNome("NOVO_NOME");
            entrada.setCpf("12345678901");
            entrada.setTelefone("NOVO_TELEFONE");

            Paciente existente = new Paciente();
            existente.setId(10L);
            existente.setNome("ANTIGO_NOME");
            existente.setCpf("00000000000");
            existente.setTelefone("ANTIGO_TELEFONE");

            when(pacienteRepository.findById(10L)).thenReturn(Optional.of(existente));

            when(pacienteRepository.save(existente)).thenReturn(existente);

            Paciente resultado = pacienteService.atualizarPaciente(entrada);

            assertEquals(entrada.getNome(), existente.getNome());
            assertEquals(entrada.getCpf(), existente.getCpf());
            assertEquals(entrada.getTelefone(), existente.getTelefone());

            verify(pacienteRepository, times(1)).save(existente);
        }


        @Test
    void deveLancarExcecaoAoAtualizarQuandoNaoExiste() {
        Paciente entrada = new Paciente();
        entrada.setId(10L);

        when(pacienteRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> pacienteService.atualizarPaciente(entrada));
        verify(pacienteRepository, never()).save(any());

    }

    @Test
    void deveDeletarQuandoIdExiste() {

    }

    @Test
    void deveLancarExcecaoAoDeletarQuandoIdNaoExiste() {

    }
}