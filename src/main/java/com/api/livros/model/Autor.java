package com.api.livros.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros = new HashSet<>();

    public Autor() {}

    public Autor(String nome, Integer anoNascimento, Integer anoFalecimento) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Integer getAnoNascimento() { return anoNascimento; }
    public Integer getAnoFalecimento() { return anoFalecimento; }
    public Set<Livro> getLivros() { return livros; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }


    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome=\"" + nome + "\"" +
                ", anoNascimento=" + anoNascimento +
                ", anoFalecimento=" + anoFalecimento +
                '}';
    }

}