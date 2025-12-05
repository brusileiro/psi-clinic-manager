package controller;

import lombok.RequiredArgsConstructor;
import model.Pagamento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PagamentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor

public class PagamentoController {
    private final PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<Pagamento>> listarTodos() {
        List<Pagamento> pagamentos = pagamentoService.listarTodos();
        return ResponseEntity.ok(pagamentos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPorId(@PathVariable Long id) {
        Optional<Pagamento> optional = pagamentoService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pagamento pagamentoEncontrado = optional.get();
        return ResponseEntity.ok(pagamentoEncontrado);
    }

    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento (@RequestBody Pagamento pagamento) {
        Pagamento pagamentoCriado = pagamentoService.registrarPagamento(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoCriado);
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
