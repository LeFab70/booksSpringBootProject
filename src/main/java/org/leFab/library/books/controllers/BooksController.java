package org.leFab.library.books.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.leFab.library.adress.entities.AddressEntity;
import org.leFab.library.adress.repositories.AdressRepository;
import org.leFab.library.authors.entities.AuthorEntity;
import org.leFab.library.authors.repositories.AuthorRepository;
import org.leFab.library.books.dto.BookRequest;
import org.leFab.library.books.dto.BookResponse;
import org.leFab.library.books.repositories.BooksRepository;
import org.leFab.library.books.services.interfaces.BooksServices;
import org.leFab.library.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Books Controller", description = "Endpoints for managing books")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BooksController {

    private final BooksServices booksServices;

//    public BooksController(BooksServices booksServices) {
//        this.booksServices = booksServices;
//    }


    @Operation(summary = "Generate Test Data", description = "Generates and adds random test data to the database if no books exist.")
    @PostMapping(value = "/testdata",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> generateTestData() {
        booksServices.generateTestData();
      return ResponseEntity.status(HttpStatus.CREATED).body("10books added");
    }


    @Operation(summary = "Add a New Book", description = "Adds a new book to the database.")
    @PostMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<BookResponse> addBook(@Valid  @RequestBody BookRequest book){
            return ResponseEntity.status(HttpStatus.CREATED).body(booksServices.addBook(book));
    }


    @Operation(summary = "Get All Books", description = "Retrieves a list of all books in the database.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BookResponse>> getAllBooks(){
        return ResponseEntity.ok(booksServices.getAllBooks());
    }

    @Operation(summary = "Get Book by ID", description = "Retrieves a book by its ID.")
    @GetMapping(value = "/book/{id}",produces = "application/json")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(booksServices.getBookById(id));
    }

    @Operation(summary = "Delete Book by ID", description = "Deletes a book by its ID.")
    @DeleteMapping(value = "/book/{id}",consumes = "application/json",produces = "application/json")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        booksServices.removeBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update Book by ID", description = "Updates the details of a book by its ID.")
    @PutMapping(value = "/book/{id}",consumes = "application/json",produces = "application/json")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest updatedBook){
        return ResponseEntity.ok(booksServices.updateBook(id,updatedBook));
    }
}
