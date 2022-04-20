package com.system.management.library.librarymanagementsystem.service;

import com.system.management.library.librarymanagementsystem.dto.BookDto;
import com.system.management.library.librarymanagementsystem.mapper.BookDtoMapper;
import com.system.management.library.librarymanagementsystem.model.Book;
import com.system.management.library.librarymanagementsystem.repository.BookRepository;
import com.system.management.library.librarymanagementsystem.validator.BookDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;
    private final BookDtoValidator bookDtoValidator;

    @Transactional
    public BookDto addBook(final BookDto bookDto, final boolean validate) {
        if (validate) {
            bookDtoValidator.authorValidator(bookDto.getAuthor());
            bookDtoValidator.titleValidator(bookDto.getTitle());
            bookDtoValidator.isbnValidator(bookDto.getIsbn());
        }

        final Book book = Book.builder()
                .author(bookDto.getAuthor().trim())
                .title(bookDto.getTitle().trim())
                .isbn(bookDto.getIsbn())
                .build();

        final Book savedBook = bookRepository.save(book);

        return bookDtoMapper.toDto(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        final List<Book> books = bookRepository.findAll();
        return bookDtoMapper.toDtos(books);
    }
}
