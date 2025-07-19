package com.api.livros;

import com.api.livros.model.Autor;
import com.api.livros.model.Livro;
import com.api.livros.repository.AutorRepository;
import com.api.livros.repository.LivroRepository;
import com.api.livros.service.LivroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MenuConsole implements CommandLineRunner {

    private final LivroService livroService;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final Scanner scanner = new Scanner(System.in);

    public MenuConsole(LivroService livroService, LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroService = livroService;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public void run(String... args) {
        int opcao;

        do {
            System.out.println("\n====== Catálogo de Livros ======");
            System.out.println("1. Buscar livro pelo título");
            System.out.println("2. Listar livros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores por ano");
            System.out.println("5. Listar livros por idioma");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            String entrada = scanner.nextLine();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas o número da opção.");
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> buscarLivro();
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresPorAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void buscarLivro() {
        System.out.print("Insira o nome do livro que você deseja procurar: ");
        String titulo = scanner.nextLine();
        livroService.buscarLivroPeloTitulo(titulo)
                .ifPresentOrElse(
                        livro -> System.out.println("Livro salvo: " + livro.getTitulo()),
                        () -> System.out.println("Livro não encontrado na API.")
                );
    }

    private void listarLivros() {
        List<Livro> livros = livroRepository.findAllWithAutores();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        livros.forEach(livro -> {
            String autores = livro.getAutores().stream()
                    .map(Autor::getNome)
                    .collect(Collectors.joining(", "));
            System.out.printf("Título: %s | Autor(es): %s | Idioma: %s | Downloads: %d%n",
                    livro.getTitulo(), autores, livro.getIdioma(), livro.getNumeroDownloads());
        });
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
            return;
        }
        autores.forEach(autor -> {
            String livros = autor.getLivros().stream()
                    .map(Livro::getTitulo)
                    .collect(Collectors.joining(", "));
            System.out.printf("Autor: %s | Nascimento: %s | Falecimento: %s | Obras: %s%n",
                    autor.getNome(),
                    Optional.ofNullable(autor.getAnoNascimento()).orElse(0),
                    Optional.ofNullable(autor.getAnoFalecimento()).orElse(0),
                    livros.isEmpty() ? "Nenhuma" : livros);
        });
    }

    private void listarAutoresPorAno() {
        System.out.print("Insira o ano que deseja pesquisar: ");
        try {
            int ano = Integer.parseInt(scanner.nextLine());
            List<Autor> autores = autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);
            if (autores.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo no ano " + ano);
            } else {
                autores.forEach(a -> System.out.println("Autor: " + a.getNome()));
            }
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido!");
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma para realizar a busca (es, en, fr, pt): ");
        String idioma = scanner.nextLine();

        List<Livro> livros = livroRepository.findByIdiomaWithAutores(idioma);


        if (livros.isEmpty()) {
            System.out.println("Não existem livros nesse idioma no banco de dados.");
        } else {
            livros.forEach(livro -> {
                String autores = livro.getAutores().stream()
                        .map(Autor::getNome)
                        .collect(Collectors.joining(", "));
                System.out.printf("Título: %s | Autor(es): %s | Idioma: %s | Downloads: %d%n",
                        livro.getTitulo(), autores, livro.getIdioma(), livro.getNumeroDownloads());
            });
        }
    }
}
