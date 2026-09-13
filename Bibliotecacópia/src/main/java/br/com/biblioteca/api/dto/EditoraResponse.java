package br.com.biblioteca.api.dto;

import br.com.biblioteca.domain.Status;

public record EditoraResponse(

        Long id,
        String razaoSocial,
        String cnpj,
        Status status

) {
}