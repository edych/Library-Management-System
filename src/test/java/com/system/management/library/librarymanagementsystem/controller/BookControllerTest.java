package com.system.management.library.librarymanagementsystem.controller;

import com.system.management.library.librarymanagementsystem.config.ZalandoProblemConfiguration;
import com.system.management.library.librarymanagementsystem.dto.BookDto;
import com.system.management.library.librarymanagementsystem.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({BookController.class, ZalandoProblemConfiguration.class})
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    final String url = "/books";

    @Test
    void addBookShouldReturnCreatedIfBookDtoIsValid() throws Exception {
        // given
        final BookDto requestBookDto = BookDto.builder()
                .author("Ane Doe")
                .title("Title")
                .isbn("978-3-16-148410-0")
                .build();

        final BookDto resultBookDto = BookDto.builder()
                .author("Ane Doe")
                .title("Title")
                .isbn("978-3-16-148410-0")
                .build();

        // when
        when(bookService.addBook(requestBookDto, true)).thenReturn(resultBookDto);

        // then
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"author\":\"" + requestBookDto.getAuthor() + "\"," +
                        "\"title\":\"" + requestBookDto.getTitle() + "\"," +
                        "\"isbn\":\"" + requestBookDto.getIsbn() + "\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains(resultBookDto.getAuthor())))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains(resultBookDto.getTitle())))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains(resultBookDto.getIsbn())));
    }

    private static Stream<Arguments> provideInputsForBookDtoValidation() {
        return Stream.of(
                Arguments.of("Ane Doe", "Title", "invalid", "Enter a valid ISBN."),
                Arguments.of("Ane Doe", "Title", "123456", "Enter a valid ISBN."),
                Arguments.of("Ane Doe", "Title", "", "Enter a valid ISBN."),
                Arguments.of("", "Title", "978-3-16-148410-0", "'Author' can not be empty."),
                Arguments.of("Ane Doe", "", "978-3-16-148410-0", "'Title' can not be empty.")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInputsForBookDtoValidation")
    void addBookShouldReturnBadRequestIfBookDtoIsInvalid(final String author, final String title, final String isbn,
                                                         final String expectedMessage) throws Exception {

        // given
        final BookDto requestBookDto = BookDto.builder()
                .author(author)
                .title(title)
                .isbn(isbn)
                .build();

        final ValidationException expectedException = new ValidationException(expectedMessage);

        // when
        when(bookService.addBook(requestBookDto, true)).thenThrow(expectedException);

        // then
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"author\":\"" + requestBookDto.getAuthor() + "\"," +
                        "\"title\":\"" + requestBookDto.getTitle() + "\"," +
                        "\"isbn\":\"" + requestBookDto.getIsbn() + "\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException))
                .andExpect(result -> assertEquals(expectedMessage,
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void getAllBooksShouldReturnOkAndListOfBooksDto() throws Exception {
        // given
        final BookDto book1 = BookDto.builder()
                .author("Ane Doe")
                .title("Title")
                .isbn("978-3-16-148410-0")
                .build();

        final BookDto book2 = BookDto.builder()
                .author("Ane Doe")
                .title("Title")
                .isbn("978-3-16-148410-0")
                .build();

        // when
        when(bookService.getAllBooks()).thenReturn(List.of(book1, book2));

        // then
        mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains(book1.getAuthor())))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains(book1.getTitle())))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains(book1.getIsbn())))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains(book2.getAuthor())))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains(book2.getTitle())))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains(book2.getIsbn())));
    }

    @Test
    void getAllBooksShouldReturnOkAndEmptyListIfThereAreNoBooksStored() throws Exception {
        // when
        when(bookService.getAllBooks()).thenReturn(List.of());

        // then
        mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)));
    }
}