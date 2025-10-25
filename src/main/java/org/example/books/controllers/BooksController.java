package org.example.books.controllers;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.example.books.dto.BookRequest;
import org.example.books.dto.BookResponse;
import org.example.books.entities.AuthorEntity;
import org.example.books.entities.BooksEntity;
import org.example.books.exceptions.BadRequestException;
import org.example.books.repositories.AuthorRepository;
import org.example.books.services.interfaces.BooksServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    //Just pour test ici
    private final AuthorRepository authorRepository;
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
    @PostMapping()
    public ResponseEntity<String> addBook(@RequestBody(required = false) BookRequest book){
        // Add 10 random books if none exist
        //exporter ceci vers le service



        if(booksServices.getAllBooks().isEmpty()){

            for (int i = 0; i < 10; i++) {
                String author = faker.book().author();
                authorRepository.save(
                        AuthorEntity.builder()
                                .first_name(author)
                                .last_name(faker.name().lastName())
                                .birth_date(LocalDate.ofEpochDay(startEpochDay + faker.number().numberBetween(0, (endEpochDay - startEpochDay))))
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

        if(book != null){
            return booksServices.addBook(book);
            //return ResponseEntity.ok("Book added successfully.");
        } else {
           throw new BadRequestException("Book data is required.");
        }

    }
    @GetMapping()
    public List<BookResponse> getAllBooks(){
        return booksServices.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public BookResponse getBookById(@PathVariable Long id){
        return booksServices.getBookById(id);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        return  booksServices.removeBook(id);
    }

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
