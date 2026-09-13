package br.com.biblioteca.repository;

import br.com.biblioteca.domain.Editora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditoraRepository extends JpaRepository<Editora, Long> {

    boolean existsByCnpj(String cnpj);
}