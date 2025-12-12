package controller;

import dto.PagamentoCreateDTO;
import dto.PagamentoDTO;
import lombok.RequiredArgsConstructor;
import model.Pagamento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PagamentoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor

public class PagamentoController {
    private final PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listarTodos() {
        List<Pagamento> pagamentos = pagamentoService.listarTodos();
        List<PagamentoDTO> pagamentoDTOS = new ArrayList<>();
        for (Pagamento p : pagamentos) {
            PagamentoDTO dto = PagamentoDTO.from(p);
            pagamentoDTOS.add(dto);
        }
        return ResponseEntity.ok(pagamentoDTOS);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> buscarPorId(@PathVariable Long id) {
        Optional<Pagamento> optional = pagamentoService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pagamento pagamentoEncontrado = optional.get();
        PagamentoDTO pagamentoDTO = PagamentoDTO.from(pagamentoEncontrado);
        return ResponseEntity.ok(pagamentoDTO);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento (@RequestBody PagamentoCreateDTO pagamentoDTO) {
        Pagamento pagamento = pagamentoDTO.toEntity();
        Pagamento pagamentoCriado = pagamentoService.registrarPagamento(pagamento);
        PagamentoDTO dto = PagamentoDTO.from(pagamentoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> deletarPagamento (@PathVariable Long id) {
    try {
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
    }
}
