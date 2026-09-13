package br.com.biblioteca.api.dto;

import br.com.biblioteca.domain.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LivroResponse(

        Long id,
        String codigoBarras,
        String descricao,
        BigDecimal saldoEstoque,
        BigDecimal valorUnitario,
        BigDecimal estoqueMinimo,
        BigDecimal valorEstoque,
        LocalDate dataCadastro,
        Status status,
        Long categoriaId,
        String categoriaNome,
        Long editoraId,
        String editoraRazaoSocial

) {
}