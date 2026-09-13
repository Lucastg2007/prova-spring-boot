package br.com.biblioteca.api.mapper;

import br.com.biblioteca.api.dto.EditoraResponse;
import br.com.biblioteca.domain.Editora;

public class EditoraMapper {

    private EditoraMapper() {
    }

    public static EditoraResponse toResponse(Editora editora) {
        return new EditoraResponse(
                editora.getId(),
                editora.getRazaoSocial(),
                editora.getCnpj(),
                editora.getStatus()
        );
    }
}