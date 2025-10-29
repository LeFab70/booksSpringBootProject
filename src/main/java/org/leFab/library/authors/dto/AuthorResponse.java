package org.leFab.library.authors.dto;

import java.util.List;

public record AuthorResponse(
        Long id,
        String name,
        Integer age,
        String address,
        List<String> bookTitles
) {
}
