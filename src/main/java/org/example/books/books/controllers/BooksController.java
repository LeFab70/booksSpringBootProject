package org.example.books.books.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.example.books.adress.entities.AddressEntity;
import org.example.books.adress.repositories.AdressRepository;
import org.example.books.authors.entities.AuthorEntity;
import org.example.books.authors.repositories.AuthorRepository;
import org.example.books.books.dto.dto.BookRequest;
import org.example.books.books.dto.dto.BookResponse;
import org.example.books.books.repositories.BooksRepository;
import org.example.books.books.services.interfaces.BooksServices;
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

    //Just pour test ici, a retirer plus tard
    private final AuthorRepository authorRepository;
    private final BooksRepository booksRepository;
    private final AdressRepository adressRepository;
    //fin pour test ici

    private final BooksServices booksServices;
    private final Faker faker = new Faker();
    LocalDate start = LocalDate.of(1950, 1, 1);
    LocalDate end = LocalDate.of(2005, 12, 31);

    long startEpochDay = start.toEpochDay();
    long endEpochDay = end.toEpochDay();
    long randomDay = startEpochDay + faker.number().numberBetween(0, (endEpochDay - startEpochDay));
//    public BooksController(BooksServices booksServices) {
//        this.booksServices = booksServices;
//    }

    // Endpoints to be implemented

    @Operation(summary = "Generate Test Data", description = "Generates and adds random test data to the database if no books exist.")
    @PostMapping("/testdata")
    public ResponseEntity<String> generateTestData() {

        if (booksServices.getAllBooks().isEmpty()) {
            for (int i=0;i<5;i++) {
                adressRepository.save(
                       AddressEntity.builder()
                                .street(faker.address().streetAddress())
                                .city(faker.address().city())
                                .state(faker.address().state())
                                .zipCode(faker.address().zipCode())
                                .build()
                );
            }

            for (int i = 0; i < 10; i++) {
                String author = faker.book().author();
                authorRepository.save(
                        AuthorEntity.builder()
                                .first_name(author)
                                .last_name(faker.name().lastName())
                                .birth_date(LocalDate.ofEpochDay(startEpochDay + faker.number().numberBetween(0, (endEpochDay - startEpochDay))))
                                .email(faker.internet().emailAddress())
                                .address(adressRepository.findAll().get(faker.number().numberBetween(0, adressRepository.findAll().size())))
                                .build()
                );
            }
            List<AuthorEntity> authors = authorRepository.findAll();
            for (int i = 0; i < 10; i++) {
                String title = faker.book().title();
                Integer pages = faker.number().numberBetween(100, 1000);
                LocalDate publishedDate = LocalDate.ofEpochDay(startEpochDay + faker.number().numberBetween(0, (endEpochDay - startEpochDay)));

                AuthorEntity randomAuthor = authors.get(faker.number().numberBetween(0, authors.size()));
                BookRequest bookRequest = new BookRequest(
                        title,
                        pages,
                        publishedDate,
                        randomAuthor.getId()
                );
                booksServices.addBook(bookRequest);
            }
            return ResponseEntity.ok("10 random books added to the database.");
        }
    else {
            return ResponseEntity.ok("Books already exist in the database. No test data added.");
        }
    }


    @Operation(summary = "Add a New Book", description = "Adds a new book to the database.")
    @PostMapping()
    public ResponseEntity<String> addBook(@Valid  @RequestBody BookRequest book){
            return booksServices.addBook(book);
            //return ResponseEntity.ok("Book added successfully.");
    }
    @Operation(summary = "Get All Books", description = "Retrieves a list of all books in the database.")
    @GetMapping()
    public List<BookResponse> getAllBooks(){
        return booksServices.getAllBooks();
    }
@Operation(summary = "Get Book by ID", description = "Retrieves a book by its ID.")
    @GetMapping("/book/{id}")
    public BookResponse getBookById(@PathVariable Long id){
        return booksServices.getBookById(id);
    }

    @Operation(summary = "Delete Book by ID", description = "Deletes a book by its ID.")
    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){

        authorRepository.deleteAll(); // Just for testing, to be removed later
        booksRepository.deleteAll(); // Just for testing, to be removed later
        adressRepository.deleteAll(); // Just for testing, to be removed later

        return  booksServices.removeBook(id);
    }

    @Operation(summary = "Update Book by ID", description = "Updates the details of a book by its ID.")
    @PutMapping("/book/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookRequest updatedBook){
        return booksServices.updateBook(id,
                new BookRequest(
                        updatedBook.title(),
                        updatedBook.pages(),
                        updatedBook.publishedDate(),
                        updatedBook.authorId()
                ));
    }
}
