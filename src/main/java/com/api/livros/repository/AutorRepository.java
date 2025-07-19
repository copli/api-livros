package com.api.livros.repository;

import com.api.livros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(Integer ano1, Integer ano2);
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}
