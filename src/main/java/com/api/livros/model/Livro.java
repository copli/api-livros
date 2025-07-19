package com.api.livros.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private int numeroDownloads;

    @ManyToMany
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    public Livro() {}

    public Livro(String titulo, String idioma, int numeroDownloads) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIdioma() { return idioma; }
    public int getNumeroDownloads() { return numeroDownloads; }
    public Set<Autor> getAutores() { return autores; }
    public void setAutores(Set<Autor> autores) { this.autores = autores; }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setNumeroDownloads(int numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }


    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo=\"" + titulo + "\"" +
                ", idioma=\"" + idioma + "\""+
                ", numeroDownloads=" + numeroDownloads +
                ", autores=" + autores.stream().map(Autor::getNome).toList() +
                '}';
    }
}