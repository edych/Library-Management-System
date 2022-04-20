package com.system.management.library.librarymanagementsystem.validator;

import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ValidationException;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
@NoArgsConstructor
public class BookDtoValidator {

    private static final Pattern AUTHOR_PATTERN = Pattern.compile("[a-zA-Z -]+");

    public void authorValidator(final String value) {
        if (Objects.isNull(value) || !StringUtils.hasText(value)) {
            throw new ValidationException("'Author' can not be empty.");
        }

        final String trimmedAuthor = value.trim();
        final boolean containsOnlyAllowedChars = AUTHOR_PATTERN.matcher(trimmedAuthor).matches();

        if (!containsOnlyAllowedChars) {
            throw new ValidationException("Please remove all special characters from 'Author' field.");
        }

        final String[] authorToValidate = trimmedAuthor.split(" ");

        if (authorToValidate.length != 2) {
            throw new ValidationException("Field 'Author' must consist of forename and surname.");
        }

        if (!authorToValidate[0].startsWith("A") && !authorToValidate[1].startsWith("A")) {
            throw new ValidationException("Forename or surname must start with 'A'.");
        }
    }

    public void titleValidator(final String value) {
        if (Objects.isNull(value) || !StringUtils.hasText(value)) {
            throw new ValidationException("'Title' can not be empty.");
        }
    }

    public void isbnValidator(final String value) {
        final ISBNValidator isbnValidator = new ISBNValidator();
        final boolean isValid = isbnValidator.isValid(value);

        if (!isValid) {
            throw new ValidationException("Enter a valid ISBN.");
        }
    }
}
