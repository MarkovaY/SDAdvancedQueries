package com.example.sdadvancedqueries.service.impl;

import com.example.sdadvancedqueries.model.entity.*;
import com.example.sdadvancedqueries.repository.BookRepository;
import com.example.sdadvancedqueries.service.AuthorService;
import com.example.sdadvancedqueries.service.BookService;
import com.example.sdadvancedqueries.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAgeRestriction(AgeRestriction ageRestriction) {
        return bookRepository.findByAgeRestrictionEquals(ageRestriction)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksWithGoldenEditionWithLessThan5000Copies() {
        return bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> printAllBookTitlesAndPricesWithPriceLessThan5OrMoreThan40() {

        return bookRepository.findAllByPriceBeforeOrPriceAfter(BigDecimal.valueOf(5), BigDecimal.valueOf(40));
    }

    @Override
    public List<String> findAllBooksWithReleaseDateDifferentThan(int year) {

        return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllBooksReleasedBefore(LocalDate date) {
        return bookRepository.findAllByReleaseDateBefore(date);
    }

    @Override
    public List<String> findAllBooksContaining(String string) {

        return bookRepository.findAllByTitleContainingIgnoreCase(string)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksWithAuthorLastNameStartingWith(String nameStart) {
        return bookRepository.findAllByAuthor_LastNameStartingWithIgnoreCase(nameStart)
                .stream()
                .map(book -> String.format("%s (%s %s)", book.getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public int printNumberOfBooksWithTitleLongerThan(int lengthOfTitle) {
        return bookRepository.countOfBooksWithTitleLongerThan(lengthOfTitle);
    }

    @Override
    public String findBookInformationByTitle(String titleGiven) {
        Book book = bookRepository.findBookByTitle(titleGiven);
        return String.format("%s %s %s %.2f", book.getTitle(), book.getEditionType(), book.getAgeRestriction(), book.getPrice());
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
