package br.com.biblioteca.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "grupo_produto")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @OneToMany(mappedBy = "categoria")
    private List<Livro> livros = new ArrayList<>();

    protected Categoria() {
    }

    public Categoria(String nome) {
        validarNome(nome);
        this.nome = nome;
        this.status = Status.ATIVO;
    }

    public void adicionarLivro(Livro livro) {
        Objects.requireNonNull(livro, "Livro é obrigatório");

        boolean jaExiste = livros.stream()
                .anyMatch(l -> Objects.equals(l.getCodigoBarras(), livro.getCodigoBarras()));

        if (jaExiste) {
            throw new IllegalArgumentException("Livro já pertence à categoria");
        }

        livros.add(livro);
        livro.associarCategoria(this);
    }

    public void ativar() {
        this.status = Status.ATIVO;
    }

    public void inativar() {
        this.status = Status.INATIVO;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Status getStatus() {
        return status;
    }

    public List<Livro> getLivros() {
        return List.copyOf(livros);
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }
    }
}