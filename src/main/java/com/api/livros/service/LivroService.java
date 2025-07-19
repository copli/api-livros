package com.api.livros.service;

import java.util.stream.Collectors;
import com.api.livros.dto.GutendexBookDTO;
import com.api.livros.dto.GutendexResponseDTO;
import com.api.livros.model.Autor;
import com.api.livros.model.Livro;
import com.api.livros.repository.AutorRepository;
import com.api.livros.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import org.springframework.web.util.UriUtils;



import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class LivroService {

    private final RestTemplate restTemplate;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroService(RestTemplate restTemplate, LivroRepository livroRepository, AutorRepository autorRepository) {
        this.restTemplate = restTemplate;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public Optional<Livro> buscarLivroPeloTitulo(String titulo) {
        String url = "https://gutendex.com/books/?search=" + UriUtils.encode(titulo, StandardCharsets.UTF_8);

        GutendexResponseDTO response = restTemplate.getForObject(url, GutendexResponseDTO.class);

        if (response == null || response.results == null || response.results.isEmpty()) {
            return Optional.empty();
        }

        GutendexBookDTO dto = response.results.get(0); // Pega o primeiro resultado
        Livro livro = new Livro();
        livro.setTitulo(dto.title);
        livro.setIdioma(dto.languages.get(0));
        livro.setNumeroDownloads(dto.download_count);

        Set<Autor> autores = dto.authors.stream().map(authorDTO -> {
            Autor autor = new Autor();
            autor.setNome(authorDTO.name);
            autor.setAnoNascimento(authorDTO.birth_year);
            autor.setAnoFalecimento(authorDTO.death_year);
            autor.getLivros().add(livro);
            return autor;
        }).collect(Collectors.toSet());

        livro.setAutores(autores);

        autorRepository.saveAll(autores); // primeiro salva os autores
        Livro salvo = livroRepository.save(livro); // depois o livro

        return Optional.of(salvo);
    }

}