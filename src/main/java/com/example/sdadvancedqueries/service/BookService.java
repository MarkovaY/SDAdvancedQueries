package com.example.sdadvancedqueries.service;

import com.example.sdadvancedqueries.model.entity.AgeRestriction;
import com.example.sdadvancedqueries.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<String> findAllBooksWithGoldenEditionWithLessThan5000Copies();

    List<Book> printAllBookTitlesAndPricesWithPriceLessThan5OrMoreThan40();

    List<String> findAllBooksWithReleaseDateDifferentThan(int year);

    List<Book> findAllBooksReleasedBefore(LocalDate date);

    List<String> findAllBooksContaining(String string);

    List<String> findAllBooksWithAuthorLastNameStartingWith(String nameStart);
}
