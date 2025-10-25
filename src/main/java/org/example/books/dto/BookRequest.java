package org.example.books.dto;

import java.time.LocalDate;

public record BookRequest(
        String title,
        Integer pages,
        LocalDate publishedDate,
        Long authorId
) {
}
