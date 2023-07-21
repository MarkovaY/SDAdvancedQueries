package com.example.sdadvancedqueries.service;

import com.example.sdadvancedqueries.model.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<Author> findNamesOfAuthorsWithFirstNameFinishingBy(String letter);
}
