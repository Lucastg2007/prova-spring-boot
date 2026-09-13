package br.com.biblioteca.api.controller;

import br.com.biblioteca.api.dto.EditoraRequest;
import br.com.biblioteca.api.dto.EditoraResponse;
import br.com.biblioteca.api.mapper.EditoraMapper;
import br.com.biblioteca.application.EditoraService;
import br.com.biblioteca.domain.Editora;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editoras")
public class EditoraController {

    private final EditoraService service;

    public EditoraController(EditoraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EditoraResponse> cadastrar(
            @Valid @RequestBody EditoraRequest request) {

        Editora editora = service.cadastrar(
                request.razaoSocial(),
                request.cnpj()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EditoraMapper.toResponse(editora));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditoraResponse> buscarPorId(
            @PathVariable Long id) {

        Editora editora = service.buscarPorId(id);

        return ResponseEntity.ok(
                EditoraMapper.toResponse(editora)
        );
    }

    @GetMapping
    public ResponseEntity<List<EditoraResponse>> listar() {

        List<EditoraResponse> editoras = service.listar()
                .stream()
                .map(EditoraMapper::toResponse)
                .toList();

        return ResponseEntity.ok(editoras);
    }
}