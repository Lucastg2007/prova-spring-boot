package br.com.biblioteca.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequest(

        @NotBlank(message = "Nome da categoria é obrigatório")
        String nome

) {
}