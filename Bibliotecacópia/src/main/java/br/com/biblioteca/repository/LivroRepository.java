package br.com.biblioteca.repository;

import br.com.biblioteca.domain.Livro;
import br.com.biblioteca.domain.Status;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByCodigoBarras(String codigoBarras);

    boolean existsByCodigoBarras(String codigoBarras);

    @EntityGraph(attributePaths = {"categoria", "editora"})
    Optional<Livro> findComCategoriaEEditoraById(Long id);

    @Query("""
        SELECT l
        FROM Livro l
        JOIN FETCH l.categoria
        LEFT JOIN FETCH l.editora
        """)
    List<Livro> findAllComCategoriaEEditora();

    List<Livro> findByCategoriaId(Long categoriaId);

    List<Livro> findByStatus(Status status);
}