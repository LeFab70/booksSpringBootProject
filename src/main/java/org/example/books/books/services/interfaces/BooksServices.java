package org.example.books.books.services.interfaces;

import org.example.books.books.dto.dto.BookRequest;
import org.example.books.books.dto.dto.BookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BooksServices {
    public ResponseEntity<String> addBook(BookRequest request);
    public ResponseEntity<String> removeBook(Long id);
    public List<BookResponse> getAllBooks();
    public BookResponse getBookById(Long id);
    public ResponseEntity<String> updateBook(Long id, BookRequest request);
}
