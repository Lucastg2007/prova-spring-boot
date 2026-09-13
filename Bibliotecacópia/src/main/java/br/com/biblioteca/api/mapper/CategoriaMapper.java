package br.com.biblioteca.api.mapper;

import br.com.biblioteca.api.dto.CategoriaResponse;
import br.com.biblioteca.domain.Categoria;

public class CategoriaMapper {

    private CategoriaMapper() {
    }

    public static CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getStatus()
        );
    }
}