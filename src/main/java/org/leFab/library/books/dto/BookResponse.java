package org.leFab.library.books.dto;

import java.time.LocalDate;

public record BookResponse(
        Long id,
        String title,
        Integer pages,
        LocalDate publishedDate,
        String authorName,
        Integer authorAge
) {
}
