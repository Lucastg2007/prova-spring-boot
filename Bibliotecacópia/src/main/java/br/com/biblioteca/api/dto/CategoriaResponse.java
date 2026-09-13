package br.com.biblioteca.api.dto;

import br.com.biblioteca.domain.Status;

public record CategoriaResponse(

        Long id,
        String nome,
        Status status

) {
}