package org.example.books.services.interfaces;

import org.example.books.dto.BookRequest;
import org.example.books.dto.BookResponse;
import org.example.books.entities.BooksEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface BooksServices {
    public ResponseEntity<String> addBook(BookRequest request);
    public ResponseEntity<String> removeBook(Long id);
    public List<BookResponse> getAllBooks();
    public BookResponse getBookById(Long id);
    public ResponseEntity<String> updateBook(Long id, BookRequest request);
}
