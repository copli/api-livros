package com.api.livros.dto;

import java.util.List;

public class GutendexBookDTO {
    public String title;
    public List<AuthorDTO> authors;
    public List<String> languages;
    public int download_count;

    public static class AuthorDTO {
        public String name;
        public Integer birth_year;
        public Integer death_year;
    }
}

