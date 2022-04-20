package com.system.management.library.librarymanagementsystem.controller;

import com.system.management.library.librarymanagementsystem.dto.BookDto;
import com.system.management.library.librarymanagementsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.ValidationException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(CREATED)
    public BookDto addBook(@RequestBody final BookDto bookDto) {
        return bookService.addBook(bookDto, true);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Problem validationExceptionHandler(final ValidationException e) {
        return Problem.valueOf(Status.BAD_REQUEST, e.getMessage());
    }
}
