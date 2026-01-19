package com.example.PsiSoftware.controller;

import com.example.PsiSoftware.dto.PacienteCreateDTO;
import com.example.PsiSoftware.dto.PacienteResponseDTO;
import com.example.PsiSoftware.dto.PacienteUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.PsiSoftware.model.Paciente;
import com.example.PsiSoftware.model.Pagamento;
import com.example.PsiSoftware.model.Sessao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PsiSoftware.service.PacienteService;
import com.example.PsiSoftware.service.PagamentoService;
import com.example.PsiSoftware.service.SessaoService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;
    private final SessaoService sessaoService;
    private final PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        List<PacienteResponseDTO> pacienteResponseDTOList = new ArrayList<>();

        for (Paciente p : pacientes) {
            PacienteResponseDTO dto = PacienteResponseDTO.from(p);
            pacienteResponseDTOList.add(dto);
        }

        return ResponseEntity.ok(pacienteResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> optional = pacienteService.buscarPorId(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Paciente paciente = optional.get();
        PacienteResponseDTO pacienteResponseDTO = PacienteResponseDTO.from(paciente);

        return ResponseEntity.ok(pacienteResponseDTO);
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> criarPaciente(@Valid @RequestBody PacienteCreateDTO pacienteDTO) {
        Paciente paciente = pacienteDTO.toEntity();
        Paciente pacienteCriado = pacienteService.criarPaciente(paciente);
        PacienteResponseDTO dto = PacienteResponseDTO.from(pacienteCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody PacienteUpdateDTO updateDTO) {
        Optional<Paciente> optional = pacienteService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Paciente paciente = optional.get();
        updateDTO.aplicarAtualizacoes(paciente);
        Paciente atualizado = pacienteService.atualizarPaciente(paciente);
        PacienteResponseDTO dto = PacienteResponseDTO.from(atualizado);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            pacienteService.deletarPaciente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/sessoes")
    public ResponseEntity<List<Sessao>> listarSessoesDoPaciente(@PathVariable Long id) {
        Optional<Paciente> optional = pacienteService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Sessao> sessoes = sessaoService.listarPorPaciente(id);
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/{id}/pagamentos")
    public ResponseEntity<List<Pagamento>> listarPagamentosDoPaciente(@PathVariable Long id) {
        Optional<Paciente> optional = pacienteService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Pagamento> pagamentos = pagamentoService.listarPorPaciente(id);
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> buscarSaldo(@PathVariable Long id) {
        Optional<Paciente> optional = pacienteService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Paciente paciente = optional.get();
        BigDecimal saldo = paciente.getSaldoAtual();

        return ResponseEntity.ok(saldo);
    }

}
