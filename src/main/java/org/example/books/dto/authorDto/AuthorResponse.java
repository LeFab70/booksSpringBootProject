package org.example.books.dto.authorDto;

import java.util.List;

public record AuthorResponse(
        Long id,
        String name,
        Integer age,
        String address,
        List<String> bookTitles
) {
}
