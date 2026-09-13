package br.com.biblioteca.api.controller;

import br.com.biblioteca.api.dto.CategoriaRequest;
import br.com.biblioteca.api.dto.CategoriaResponse;
import br.com.biblioteca.api.mapper.CategoriaMapper;
import br.com.biblioteca.application.CategoriaService;
import br.com.biblioteca.domain.Categoria;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> cadastrar(
            @Valid @RequestBody CategoriaRequest request) {

        Categoria categoria = service.cadastrar(request.nome());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaMapper.toResponse(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(
            @PathVariable Long id) {

        Categoria categoria = service.buscarPorId(id);

        return ResponseEntity.ok(
                CategoriaMapper.toResponse(categoria)
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {

        List<CategoriaResponse> categorias = service.listar()
                .stream()
                .map(CategoriaMapper::toResponse)
                .toList();

        return ResponseEntity.ok(categorias);
    }
}