package com.system.management.library.librarymanagementsystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@Builder
@Value
@RequiredArgsConstructor
public class BookDto {

    Long id;
    String author;
    String title;
    String isbn;
}
