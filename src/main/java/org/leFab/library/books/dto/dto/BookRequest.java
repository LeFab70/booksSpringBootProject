package org.leFab.library.books.dto.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record BookRequest(
        @NotBlank(message = "Title must not be blank")
        @NotNull(message = "Title must not be null")
        String title,
        @Min(value = 1, message = "Pages must be at least 1")
        @Max(value = 10000, message = "Pages must not exceed 10000")
        Integer pages,
        @PastOrPresent(message = "Published date must be in the past or present")
        LocalDate publishedDate,
        @NotNull(message = "Author ID must not be null")
        Long authorId
) {
}
