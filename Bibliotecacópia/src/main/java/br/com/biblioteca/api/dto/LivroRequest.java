package br.com.biblioteca.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LivroRequest(

        @NotBlank(message = "Código de barras é obrigatório")
        String codigoBarras,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotNull(message = "Saldo de estoque é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "Saldo de estoque não pode ser negativo")
        BigDecimal saldoEstoque,

        @NotNull(message = "Valor unitário é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "Valor unitário não pode ser negativo")
        BigDecimal valorUnitario,

        @NotNull(message = "Estoque mínimo é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "Estoque mínimo não pode ser negativo")
        BigDecimal estoqueMinimo,

        @NotNull(message = "Categoria é obrigatória")
        Long categoriaId,

        Long editoraId

) {
}