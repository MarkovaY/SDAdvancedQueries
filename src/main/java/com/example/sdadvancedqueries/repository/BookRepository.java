package com.example.sdadvancedqueries.repository;

import com.example.sdadvancedqueries.model.entity.AgeRestriction;
import com.example.sdadvancedqueries.model.entity.Book;
import com.example.sdadvancedqueries.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findByAgeRestrictionEquals(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceBeforeOrPriceAfter(BigDecimal priceLow, BigDecimal priceHigh);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate start, LocalDate end);

    List<Book> findAllByTitleContainingIgnoreCase(String string);

    List<Book> findAllByAuthor_LastNameStartingWithIgnoreCase(String nameStart);

    @Query("SELECT COUNT (b) FROM Book AS b WHERE length(b.title) > :param")
    int countOfBooksWithTitleLongerThan(@Param(value = "param") int lengthOfTitle);
}
