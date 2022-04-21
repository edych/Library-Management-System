package com.system.management.library.librarymanagementsystem.service;

import com.system.management.library.librarymanagementsystem.dto.BookDto;
import com.system.management.library.librarymanagementsystem.mapper.BookDtoMapperImpl;
import com.system.management.library.librarymanagementsystem.model.Book;
import com.system.management.library.librarymanagementsystem.repository.BookRepository;
import com.system.management.library.librarymanagementsystem.validator.BookDtoValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BookService.class, BookDtoMapperImpl.class, BookDtoValidator.class})

class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @ParameterizedTest
    @ValueSource(strings = {"Ane Doe", "     Ane Doe", "   Ane Doe    ", "Doe Ane"})
    void addBookShouldReturnBookDto(final String author) {
        // given
        final BookDto bookDto = buildBookDto(author, "Pride and Prejudice", "978-3-16-148410-0");
        final Book book = buildBook(1L, author, "Pride and Prejudice", "978-3-16-148410-0");

        // when
        when(bookRepository.save(any())).thenReturn(book);

        // then
        final BookDto responseDto = bookService.addBook(bookDto, false);

        assertEquals(bookDto.getAuthor(), responseDto.getAuthor());
        assertEquals(bookDto.getTitle(), responseDto.getTitle());
        assertEquals(bookDto.getIsbn(), responseDto.getIsbn());
    }

    private static Stream<Arguments> provideStringsForAuthorValidation() {
        return Stream.of(
                Arguments.of("Ane Do.e", "Please remove all special characters from 'Author' field."),
                Arguments.of("Ane Do!e", "Please remove all special characters from 'Author' field."),
                Arguments.of("Ane Do{e", "Please remove all special characters from 'Author' field."),
                Arguments.of("Ane", "Field 'Author' must consist of forename and surname."),
                Arguments.of("A B C", "Field 'Author' must consist of forename and surname."),
                Arguments.of("Jane Doe", "Forename or surname must start with 'A'.")
        );
    }

    @ParameterizedTest
    @MethodSource("provideStringsForAuthorValidation")
    void addBookShouldThrowValidationExceptionWhenAuthorIsNotValid(final String author, final String expectedMessage) {
        // given
        final BookDto bookDto = buildBookDto(author, "Title", "978-3-16-148410-0");

        // then
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> bookService.addBook(bookDto, true));

        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> provideStringsForBookDtoValidation() {
        return Stream.of(
                Arguments.of("Ane Doe", "Title", "invalid", "Enter a valid ISBN."),
                Arguments.of("Ane Doe", "Title", "123456", "Enter a valid ISBN."),
                Arguments.of("Ane Doe", "Title", null, "Enter a valid ISBN."),
                Arguments.of("Ane Doe", "Title", "", "Enter a valid ISBN."),
                Arguments.of("", "Title", "978-3-16-148410-0", "'Author' can not be empty."),
                Arguments.of(null, "Title", "978-3-16-148410-0", "'Author' can not be empty."),
                Arguments.of("Ane Doe", "", "978-3-16-148410-0", "'Title' can not be empty."),
                Arguments.of("Ane Doe", null, "978-3-16-148410-0", "'Title' can not be empty.")

        );
    }

    @ParameterizedTest
    @MethodSource("provideStringsForBookDtoValidation")
    void addBookShouldThrowValidationExceptionWhenBookDtoIsNotValid(final String author, final String title,
                                                                    final String isbn, final String expectedMessage) {

        // given
        final BookDto bookDto = buildBookDto(author, title, isbn);

        // then
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> bookService.addBook(bookDto, true));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getAllBooksShouldReturnBookDtos() {
        // given
        final Book book1 = buildBook(1L, "Anne Doe", "Title", "978-3-16-148410-0");
        final Book book2 = buildBook(2L, "Anne Doe", "Title2", "978-3-16-148410-1");
        final Book book3 = buildBook(3L, "Anne Doe", "Title3", "978-3-16-148410-2");
        final List<Book> books = List.of(book1, book2, book3);

        // when
        when(bookRepository.findAll()).thenReturn(books);

        // then
        final List<BookDto> responseBooks = bookService.getAllBooks();

        assertEquals(3, responseBooks.size());
        assertEquals(book1.getAuthor(), responseBooks.get(0).getAuthor());
        assertEquals(book2.getAuthor(), responseBooks.get(1).getAuthor());
        assertEquals(book3.getAuthor(), responseBooks.get(2).getAuthor());

        assertEquals(book1.getTitle(), responseBooks.get(0).getTitle());
        assertEquals(book2.getTitle(), responseBooks.get(1).getTitle());
        assertEquals(book3.getTitle(), responseBooks.get(2).getTitle());

        assertEquals(book1.getIsbn(), responseBooks.get(0).getIsbn());
        assertEquals(book2.getIsbn(), responseBooks.get(1).getIsbn());
        assertEquals(book3.getIsbn(), responseBooks.get(2).getIsbn());
    }

    private BookDto buildBookDto(final String author, final String title, final String isbn) {
        return BookDto.builder()
                .author(author)
                .title(title)
                .isbn(isbn)
                .build();
    }

    private Book buildBook(final Long id, final String author, final String title, final String isbn) {
        return Book.builder()
                .id(id)
                .author(author)
                .title(title)
                .isbn(isbn)
                .build();
    }
}
