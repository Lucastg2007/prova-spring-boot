package br.com.biblioteca.application;

import br.com.biblioteca.domain.Categoria;
import br.com.biblioteca.domain.Editora;
import br.com.biblioteca.domain.Livro;
import br.com.biblioteca.exception.RecursoDuplicadoException;
import br.com.biblioteca.exception.RecursoNaoEncontradoException;
import br.com.biblioteca.repository.CategoriaRepository;
import br.com.biblioteca.repository.EditoraRepository;
import br.com.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final CategoriaRepository categoriaRepository;
    private final EditoraRepository editoraRepository;

    public LivroService(
            LivroRepository livroRepository,
            CategoriaRepository categoriaRepository,
            EditoraRepository editoraRepository) {

        this.livroRepository = livroRepository;
        this.categoriaRepository = categoriaRepository;
        this.editoraRepository = editoraRepository;
    }

    @Transactional
    public Livro cadastrar(
            Livro livro,
            Long categoriaId,
            Long editoraId) {

        if (livroRepository.existsByCodigoBarras(livro.getCodigoBarras())) {
            throw new RecursoDuplicadoException(
                    "Código de barras já cadastrado"
            );
        }

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Categoria não encontrada"
                        )
                );

        categoria.adicionarLivro(livro);

        if (editoraId != null) {
            Editora editora = editoraRepository.findById(editoraId)
                    .orElseThrow(() ->
                            new RecursoNaoEncontradoException(
                                    "Editora não encontrada"
                            )
                    );

            livro.associarEditora(editora);
        }

        return livroRepository.save(livro);
    }

    @Transactional(readOnly = true)
    public Livro buscarPorId(Long id) {
        return livroRepository.findComCategoriaEEditoraById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Livro não encontrado"
                        )
                );
    }

    @Transactional(readOnly = true)
    public List<Livro> listar() {
        return livroRepository.findAllComCategoriaEEditora();
    }
}