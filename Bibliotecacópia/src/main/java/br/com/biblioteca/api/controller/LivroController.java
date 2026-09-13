package br.com.biblioteca.api.controller;

import br.com.biblioteca.api.dto.LivroRequest;
import br.com.biblioteca.api.dto.LivroResponse;
import br.com.biblioteca.api.mapper.LivroMapper;
import br.com.biblioteca.application.LivroService;
import br.com.biblioteca.domain.Livro;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LivroResponse> cadastrar(
            @Valid @RequestBody LivroRequest request) {

        Livro livro = new Livro(
                request.codigoBarras(),
                request.descricao(),
                request.saldoEstoque(),
                request.valorUnitario(),
                request.estoqueMinimo(),
                LocalDate.now()
        );

        Livro livroSalvo = service.cadastrar(
                livro,
                request.categoriaId(),
                request.editoraId()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(LivroMapper.toResponse(livroSalvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarPorId(
            @PathVariable Long id) {

        Livro livro = service.buscarPorId(id);

        return ResponseEntity.ok(
                LivroMapper.toResponse(livro)
        );
    }

    @GetMapping
    public ResponseEntity<List<LivroResponse>> listar() {

        List<LivroResponse> livros = service.listar()
                .stream()
                .map(LivroMapper::toResponse)
                .toList();

        return ResponseEntity.ok(livros);
    }
}