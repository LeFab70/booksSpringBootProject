package org.example.books.services.interfaces;

import org.example.books.dto.booksDto.BookRequest;
import org.example.books.dto.booksDto.BookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BooksServices {
    public ResponseEntity<String> addBook(BookRequest request);
    public ResponseEntity<String> removeBook(Long id);
    public List<BookResponse> getAllBooks();
    public BookResponse getBookById(Long id);
    public ResponseEntity<String> updateBook(Long id, BookRequest request);
}
