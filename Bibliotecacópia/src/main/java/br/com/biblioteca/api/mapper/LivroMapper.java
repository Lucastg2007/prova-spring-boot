package br.com.biblioteca.api.mapper;

import br.com.biblioteca.api.dto.LivroResponse;
import br.com.biblioteca.domain.Editora;
import br.com.biblioteca.domain.Livro;

public class LivroMapper {

    private LivroMapper() {
    }

    public static LivroResponse toResponse(Livro livro) {

        Editora editora = livro.getEditora();

        return new LivroResponse(
                livro.getId(),
                livro.getCodigoBarras(),
                livro.getDescricao(),
                livro.getSaldoEstoque(),
                livro.getValorUnitario(),
                livro.getEstoqueMinimo(),
                livro.calcularValorEstoque(),
                livro.getDataCadastro(),
                livro.getStatus(),
                livro.getCategoria().getId(),
                livro.getCategoria().getNome(),
                editora != null ? editora.getId() : null,
                editora != null ? editora.getRazaoSocial() : null
        );
    }
}