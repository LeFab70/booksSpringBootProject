package org.example.books.services.interfaces;

import org.example.books.entities.BooksEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BooksServices {
    public ResponseEntity<String> addBook(String title, String author, Integer pages);
    public void removeBook(Long id);
    public List<BooksEntity> getAllBooks();
    public BooksEntity getBookById(Long id);
}
