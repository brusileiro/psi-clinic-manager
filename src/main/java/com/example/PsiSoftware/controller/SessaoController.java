package com.example.PsiSoftware.controller;

import com.example.PsiSoftware.dto.SessaoCreateDTO;
import com.example.PsiSoftware.dto.SessaoDTO;
import com.example.PsiSoftware.dto.SessaoUpdateDTO;
import lombok.RequiredArgsConstructor;
import com.example.PsiSoftware.model.Sessao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PsiSoftware.service.SessaoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sessoes")
@RequiredArgsConstructor

public class SessaoController {

    private final SessaoService sessaoService;

    @GetMapping
    public ResponseEntity<List<SessaoDTO>> listarTodas() {
        List<Sessao> sessoes = sessaoService.listarTodas();
        List<SessaoDTO> sessaoDTOS = new ArrayList<>();
        for (Sessao s : sessoes) {
            SessaoDTO dto = SessaoDTO.from(s);
            sessaoDTOS.add(dto);
        }
        return ResponseEntity.ok(sessaoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoDTO> buscarPorId (@PathVariable Long id) {
        Optional<Sessao> optional = sessaoService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Sessao sessao = optional.get();
        SessaoDTO sessaoDTO = SessaoDTO.from(sessao);
        return ResponseEntity.ok(sessaoDTO);
    }

    @PostMapping
    public ResponseEntity<SessaoDTO> criarSessao (@RequestBody SessaoCreateDTO sessaoDTO) {
        Sessao sessao = sessaoDTO.toEntity();
        Sessao sessaoCriada = sessaoService.registrarSessao(sessao);
        SessaoDTO dto = SessaoDTO.from(sessaoCriada);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessaoDTO> atualizarSessao (@PathVariable Long id, @RequestBody SessaoUpdateDTO dto) {
        Optional<Sessao> optional = sessaoService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Sessao sessaoEncontrada = optional.get();
        dto.aplicarAtualizacoes(sessaoEncontrada);
        Sessao atualizada = sessaoService.atualizarSessao(sessaoEncontrada);
        SessaoDTO sessaoDTO = SessaoDTO.from(atualizada);
        return ResponseEntity.ok(sessaoDTO);
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
