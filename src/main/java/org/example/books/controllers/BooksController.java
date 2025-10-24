package org.example.books.controllers;

import net.datafaker.Faker;
import org.example.books.entities.BooksEntity;
import org.example.books.services.interfaces.BooksServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    private final BooksServices booksServices;
    private final Faker faker = new Faker();
    public BooksController(BooksServices booksServices) {
        this.booksServices = booksServices;
    }

    // Endpoints to be implemented
    @PostMapping()
    public ResponseEntity<String> addBook(@RequestBody(required = false) BooksEntity book){
        // Add 10 random books if none exist
        if(booksServices.getAllBooks().isEmpty()){
            for (int i = 0; i < 10; i++) {
                String title = faker.book().title();
                String author = faker.book().author();
                Integer pages = faker.number().numberBetween(100, 1000);
                booksServices.addBook(title, author, pages);
            }
            return ResponseEntity.ok("10 random books added to the database.");
        }

        if(book != null){
            return booksServices.addBook(book.getTitle(), book.getAuthor(), book.getPages());
            //return ResponseEntity.ok("Book added successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid book data. Empty body.");
        }

    }
    @GetMapping()
    public List<BooksEntity> getAllBooks(){
        return booksServices.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public BooksEntity getBookById(@PathVariable Long id){
        return booksServices.getBookById(id);
    }
}
