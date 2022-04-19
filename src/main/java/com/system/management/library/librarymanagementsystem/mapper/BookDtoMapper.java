package com.system.management.library.librarymanagementsystem.mapper;

import com.system.management.library.librarymanagementsystem.dto.BookDto;
import com.system.management.library.librarymanagementsystem.model.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookDtoMapper {

    BookDto toDto(Book book);

    List<BookDto> toDtos(List<Book> books);
}
