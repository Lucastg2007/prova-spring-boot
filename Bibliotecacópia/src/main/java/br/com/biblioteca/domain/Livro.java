package br.com.biblioteca.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "produto",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_produto_codigo_barras",
                columnNames = "codigo_barras"))
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_barras", nullable = false, length = 50)
    private String codigoBarras;

    @Column(nullable = false, length = 150)
    private String descricao;

    @Column(name = "saldo_estoque", nullable = false, precision = 18, scale = 3)
    private BigDecimal saldoEstoque;

    @Column(name = "valor_unitario", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "estoque_minimo", nullable = false, precision = 18, scale = 3)
    private BigDecimal estoqueMinimo;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grupo_produto_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private Editora editora;

    protected Livro() {
    }

    public Livro(
            String codigoBarras,
            String descricao,
            BigDecimal saldoEstoque,
            BigDecimal valorUnitario,
            LocalDate dataCadastro) {

        this(
                codigoBarras,
                descricao,
                saldoEstoque,
                valorUnitario,
                BigDecimal.ZERO,
                dataCadastro
        );
    }

    public Livro(
            String codigoBarras,
            String descricao,
            BigDecimal saldoEstoque,
            BigDecimal valorUnitario,
            BigDecimal estoqueMinimo,
            LocalDate dataCadastro) {

        validarCodigoBarras(codigoBarras);
        validarDescricao(descricao);
        validarQuantidade(saldoEstoque, "Saldo de estoque");
        validarValor(valorUnitario);
        validarQuantidade(estoqueMinimo, "Estoque mínimo");

        this.codigoBarras = codigoBarras;
        this.descricao = descricao;
        this.saldoEstoque = saldoEstoque;
        this.valorUnitario = valorUnitario;
        this.estoqueMinimo = estoqueMinimo;
        this.dataCadastro = Objects.requireNonNull(
                dataCadastro,
                "Data de cadastro é obrigatória"
        );
        this.status = Status.ATIVO;
    }

    public BigDecimal calcularValorEstoque() {
        return saldoEstoque.multiply(valorUnitario);
    }

    public void receberEstoque(BigDecimal quantidade) {
        validarQuantidadePositiva(quantidade);
        this.saldoEstoque = this.saldoEstoque.add(quantidade);
    }

    public void retirarEstoque(BigDecimal quantidade) {
        validarQuantidadePositiva(quantidade);

        if (saldoEstoque.compareTo(quantidade) < 0) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }

        this.saldoEstoque = this.saldoEstoque.subtract(quantidade);
    }

    public void alterarDescricao(String descricao) {
        validarDescricao(descricao);
        this.descricao = descricao;
    }

    public void alterarValorUnitario(BigDecimal valorUnitario) {
        validarValor(valorUnitario);
        this.valorUnitario = valorUnitario;
    }

    public void ativar() {
        this.status = Status.ATIVO;
    }

    public void inativar() {
        this.status = Status.INATIVO;
    }

    public void associarCategoria(Categoria categoria) {
        Objects.requireNonNull(categoria, "Categoria é obrigatória");

        if (this.categoria != null && !Objects.equals(this.categoria, categoria)) {
            throw new IllegalStateException(
                    "Livro já está associado a outra categoria"
            );
        }

        this.categoria = categoria;
    }

    public void associarEditora(Editora editora) {
        this.editora = Objects.requireNonNull(
                editora,
                "Editora é obrigatória"
        );
    }

    public Long getId() {
        return id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getSaldoEstoque() {
        return saldoEstoque;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Status getStatus() {
        return status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Editora getEditora() {
        return editora;
    }

    private void validarCodigoBarras(String codigoBarras) {
        if (codigoBarras == null || codigoBarras.isBlank()) {
            throw new IllegalArgumentException(
                    "Código de barras é obrigatório"
            );
        }
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException(
                    "Descrição é obrigatória"
            );
        }
    }

    private void validarQuantidade(
            BigDecimal quantidade,
            String campo) {

        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    campo + " não pode ser negativo"
            );
        }
    }

    private void validarQuantidadePositiva(BigDecimal quantidade) {
        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Quantidade deve ser maior que zero"
            );
        }
    }

    private void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Valor Unitário Não Pode Ser Negativo"
            );
        }
    }
}