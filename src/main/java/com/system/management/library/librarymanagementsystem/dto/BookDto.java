package com.system.management.library.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    @NotEmpty
    @NotBlank
    String author;

    @NotEmpty
    @NotBlank
    String title;

    @NotEmpty
    @NotBlank
    @ISBN
    String isbn;
}
