package br.com.biblioteca.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EditoraRequest(

        @NotBlank(message = "Razão social é obrigatória")
        String razaoSocial,

        @NotBlank(message = "CNPJ é obrigatório")
        @Pattern(
                regexp = "\\d{14}",
                message = "CNPJ deve possuir 14 dígitos"
        )
        String cnpj

) {
}