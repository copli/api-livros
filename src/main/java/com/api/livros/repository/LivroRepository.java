package com.api.livros.repository;

import com.api.livros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByIdioma(String idioma);
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    // NOVO: Carrega livros com autores já "fetchados"
    @Query("SELECT DISTINCT l FROM Livro l LEFT JOIN FETCH l.autores")
    List<Livro> findAllWithAutores();

    // NOVO: Carrega livros por idioma com autores também
    @Query("SELECT DISTINCT l FROM Livro l LEFT JOIN FETCH l.autores WHERE l.idioma = :idioma")
    List<Livro> findByIdiomaWithAutores(@Param("idioma") String idioma);
}
