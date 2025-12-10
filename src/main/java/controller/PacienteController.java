package controller;

import dto.PacienteCreateDTO;
import dto.PacienteDTO;
import dto.PacienteUpdateDTO;
import lombok.RequiredArgsConstructor;
import model.Paciente;
import model.Pagamento;
import model.Sessao;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PacienteService;
import service.PagamentoService;
import service.SessaoService;

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
    public ResponseEntity<List<PacienteDTO>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        List<PacienteDTO> pacienteDTOList = new ArrayList<>();
        for (Paciente p : pacientes) {
            PacienteDTO dto = PacienteDTO.from(p);
            pacienteDTOList.add(dto);
        }
        return ResponseEntity.ok(pacienteDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> optional = pacienteService.buscarPorId(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Paciente paciente = optional.get();
        PacienteDTO pacienteDTO = PacienteDTO.from(paciente);

        return ResponseEntity.ok(pacienteDTO);
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> criarPaciente(@RequestBody PacienteCreateDTO pacienteDTO) {
        Paciente paciente = pacienteDTO.toEntity();
        Paciente pacienteCriado = pacienteService.criarPaciente(paciente);
        PacienteDTO dto = PacienteDTO.from(pacienteCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> atualizar(@PathVariable Long id, @RequestBody PacienteUpdateDTO updateDTO) {
        Optional<Paciente> optional = pacienteService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Paciente paciente = optional.get();
        paciente.setId(id);
        updateDTO.aplicarAtualizacoes(paciente);
        Paciente atualizado = pacienteService.atualizarPaciente(paciente);
        PacienteDTO dto = PacienteDTO.from(atualizado);

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
