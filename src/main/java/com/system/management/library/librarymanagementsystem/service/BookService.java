package com.system.management.library.librarymanagementsystem.service;

import com.system.management.library.librarymanagementsystem.dto.BookDto;
import com.system.management.library.librarymanagementsystem.mapper.BookDtoMapper;
import com.system.management.library.librarymanagementsystem.model.Book;
import com.system.management.library.librarymanagementsystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final Pattern AUTHOR_PATTERN = Pattern.compile("[a-zA-Z -]+");
    private final Validator validator;
    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;

    @Transactional
    public BookDto addBook(final BookDto bookDto) {
        validateBookDto(bookDto);

        final String validatedAuthor = validateAuthor(bookDto.getAuthor());

        final Book book = Book.builder()
                .author(validatedAuthor)
                .title(bookDto.getTitle().trim())
                .isbn(bookDto.getIsbn())
                .build();

        final Book savedBook = bookRepository.save(book);

        return bookDtoMapper.toDto(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    private String validateAuthor(final String author) {
        final String trimmedAuthor = author.trim();

        final boolean containsOnlyAllowedChars = AUTHOR_PATTERN.matcher(trimmedAuthor).matches();

        if (!containsOnlyAllowedChars) {
            throw new ValidationException("Please remove all special characters from 'author' field.");
        }

        final String[] authorToValidate = trimmedAuthor.split(" ");

        if (authorToValidate.length != 2) {
            throw new ValidationException("Field 'author' must consist of forename and surname.");
        }

        if (!authorToValidate[0].startsWith("A") && !authorToValidate[1].startsWith("A")) {
            throw new ValidationException("Forename or surname must start with 'A'.");
        }

        return trimmedAuthor;
    }

    private void validateBookDto(final BookDto bookDto) {
        final String constraintViolations = validator.validate(bookDto).toString();

        if (constraintViolations.contains("ISBN") || constraintViolations.contains("isbn")) {
            throw new ValidationException("Enter a valid ISBN.");
        }

        if (constraintViolations.contains("author")) {
            throw new ValidationException("Field 'author' must consist of forename and surname.");
        }

        if (constraintViolations.contains("title")) {
            throw new ValidationException("Field 'title' can not be empty.");
        }
    }
}
