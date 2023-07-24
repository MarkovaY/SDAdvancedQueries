package com.example.sdadvancedqueries;

import com.example.sdadvancedqueries.model.entity.AgeRestriction;
import com.example.sdadvancedqueries.model.entity.Book;
import com.example.sdadvancedqueries.service.AuthorService;
import com.example.sdadvancedqueries.service.BookService;
import com.example.sdadvancedqueries.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        printALLlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

//        1. Write a program that prints the titles of all books, for which the age restriction matches the given input (minor, teen or adult).
//        Ignore the casing of the input.

//        findAllBooksByAgeRestriction();


//        2. Write a program that prints the titles of the golden edition books, which have less than 5000 copies.

//        findAllBooksWithGoldenEditionAndWithLessThan5000Copies();


//        3. Write a program that prints the titles and prices of books with price lower than 5 and higher than 40.

//        printAllBookTitlesAndPricesWithPriceLessThan5OrMoreThan40();


//        4. Write a program that prints the titles of all books that are NOT released in a given year.

//        printAllBooksWithReleaseDateDifferentThan();


//        5. Write a program that prints the title, the edition type and the price of books, which are released before a given date.
//        The date will be in the format dd-MM-yyyy.

//        findAllBooksReleasedBeforeDate();


//        6. Write a program that prints the names of those authors, whose first name ends with a given string.

//        findNamesOfAuthorsWithFirstNameFinishingBy();


//        7. Write a program that prints the titles of books, which contain a given string (regardless of the casing).

//        printAllBooksContainingTheString();


//        8. Write a program that prints the titles of books, which are written by authors, whose last name starts with a given string.

//        printBooksWithAuthorLastNameStartingWith();


//        9. Write a program that prints the number of books, whose title is longer than a given number.

//        printNumberOfBooksWithTitleLongerThan();


//        10. Write a program that prints the total number of book copies by author. Order the results descending by total book copies.

        printTotalBookCopiesByAuthorDesc();
    }

    private void printTotalBookCopiesByAuthorDesc() {
        authorService.printTotalBookCopiesByAuthorDesc().forEach(System.out::println);
    }

    private void printNumberOfBooksWithTitleLongerThan() throws IOException {
        int lengthOfTitle = Integer.parseInt(bufferedReader.readLine());
        System.out.println(bookService.printNumberOfBooksWithTitleLongerThan(lengthOfTitle));
    }

    private void printBooksWithAuthorLastNameStartingWith() throws IOException {
        String nameStart = bufferedReader.readLine();
        bookService.findAllBooksWithAuthorLastNameStartingWith(nameStart)
                .forEach(System.out::println);
    }

    private void printAllBooksContainingTheString() throws IOException {
        String string = bufferedReader.readLine();
        bookService.findAllBooksContaining(string).forEach(System.out::println);
    }

    private void findNamesOfAuthorsWithFirstNameFinishingBy() throws IOException {
        String endOfName = bufferedReader.readLine();
        authorService.findNamesOfAuthorsWithFirstNameFinishingBy(endOfName)
                .forEach(author -> System.out.printf("%s %s%n", author.getFirstName(), author.getLastName()));
    }

    private void findAllBooksReleasedBeforeDate() throws IOException {
        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        bookService.findAllBooksReleasedBefore(localDate)
                .forEach(book -> System.out.printf("%s %s %.2f%n", book.getTitle(), book.getEditionType(), book.getPrice()));
    }
    private void printAllBooksWithReleaseDateDifferentThan() throws IOException {
        int year = Integer.parseInt(bufferedReader.readLine());
        bookService.findAllBooksWithReleaseDateDifferentThan(year)
                .forEach(System.out::println);
    }

    private void printAllBookTitlesAndPricesWithPriceLessThan5OrMoreThan40() {
        bookService.printAllBookTitlesAndPricesWithPriceLessThan5OrMoreThan40()
                .forEach(book -> System.out.printf("%s - $%s%n", book.getTitle(), book.getPrice()));
    }

    private void findAllBooksWithGoldenEditionAndWithLessThan5000Copies() {
        bookService
                .findAllBooksWithGoldenEditionWithLessThan5000Copies()
                .forEach(System.out::println);
    }

    private void findAllBooksByAgeRestriction() throws IOException {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(bufferedReader.readLine().toUpperCase());
        bookService
                .findAllBooksByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    private void printALLlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
