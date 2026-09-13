package br.com.biblioteca.application;

import br.com.biblioteca.domain.Editora;
import br.com.biblioteca.exception.RecursoDuplicadoException;
import br.com.biblioteca.exception.RecursoNaoEncontradoException;
import br.com.biblioteca.repository.EditoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EditoraService {

    private final EditoraRepository repository;

    public EditoraService(EditoraRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Editora cadastrar(String razaoSocial, String cnpj) {

        if (repository.existsByCnpj(cnpj)) {
            throw new RecursoDuplicadoException(
                    "CNPJ da editora já cadastrado"
            );
        }

        Editora editora = new Editora(razaoSocial, cnpj);

        return repository.save(editora);
    }

    @Transactional(readOnly = true)
    public Editora buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Editora não encontrada"
                        )
                );
    }

    @Transactional(readOnly = true)
    public List<Editora> listar() {
        return repository.findAll();
    }
}