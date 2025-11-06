package org.leFab.library.books.services.interfaces;

import org.leFab.library.books.dto.BookRequest;
import org.leFab.library.books.dto.BookResponse;

import java.util.List;

public interface BooksServices {
    public BookResponse addBook(BookRequest request);
    public void removeBook(Long id);
    public List<BookResponse> getAllBooks();
    public BookResponse getBookById(Long id);
    public BookResponse updateBook(Long id, BookRequest request);

    void generateTestData();
}
