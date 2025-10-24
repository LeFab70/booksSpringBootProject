package org.example.books.services.implement;

import org.example.books.entities.BooksEntity;
import org.example.books.repositories.BooksRepository;
import org.example.books.services.interfaces.BooksServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServicesImplement implements BooksServices {

    private final BooksRepository booksRepository;

    public BooksServicesImplement(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public ResponseEntity<String> addBook(String title, String author, Integer pages) {

        if (!booksRepository.findByTitleAndAuthorAndPages(title, author, pages).isEmpty()) {
            // Book already exists, do not add duplicate
            return ResponseEntity.badRequest().body("Book already exists in the database.");
        }


        BooksEntity book = BooksEntity.builder()
                .title(title)
                .author(author)
                .pages(pages)
                .build();
        booksRepository.save(book);
        return ResponseEntity.ok("Book added successfully.");
    }

    @Override
    public void removeBook(Long id) {
        booksRepository.deleteById(id);
    }

    @Override
    public List<BooksEntity> getAllBooks() {
        return (booksRepository.findAll());
    }

    @Override
    public BooksEntity getBookById(Long id) {
        return booksRepository.findById(id).orElse(null);
    }
}
