package br.com.biblioteca.application;

import br.com.biblioteca.domain.Categoria;
import br.com.biblioteca.exception.RecursoDuplicadoException;
import br.com.biblioteca.exception.RecursoNaoEncontradoException;
import br.com.biblioteca.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Categoria cadastrar(String nome) {

        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new RecursoDuplicadoException(
                    "Nome da categoria já cadastrado"
            );
        }

        Categoria categoria = new Categoria(nome);

        return repository.save(categoria);
    }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Categoria não encontrada"
                        )
                );
    }

    @Transactional(readOnly = true)
    public List<Categoria> listar() {
        return repository.findAll();
    }
}