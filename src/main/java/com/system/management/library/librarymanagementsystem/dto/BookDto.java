package com.system.management.library.librarymanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    @Schema(description = "Book author")
    String author;

    @Schema(description = "Book title")
    String title;

    @Schema(description = "Book ISBN")
    String isbn;
}
