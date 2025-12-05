package controller;

import lombok.RequiredArgsConstructor;
import model.Sessao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.SessaoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sessoes")
@RequiredArgsConstructor

public class SessaoController {

    private final SessaoService sessaoService;

    @GetMapping
    public ResponseEntity<List<Sessao>> listarTodas() {
        List<Sessao> sessoes = sessaoService.listarTodas();
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> buscarPorId (@PathVariable Long id) {
        Optional<Sessao> optional = sessaoService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Sessao sessao = optional.get();
        return ResponseEntity.ok(sessao);
    }

    @PostMapping
    public ResponseEntity<Sessao> criarSessao (@RequestBody Sessao sessao) {
        Sessao sessaoCriada = sessaoService.registrarSessao(sessao);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sessao> atualizarSessao (@PathVariable Long id, @RequestBody Sessao sessao) {
        Optional<Sessao> optional = sessaoService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        sessao.setId(id);

        Sessao atualizada = sessaoService.atualizarSessao(sessao);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSessao (@PathVariable Long id) {
        try {
            sessaoService.deletarSessao(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
