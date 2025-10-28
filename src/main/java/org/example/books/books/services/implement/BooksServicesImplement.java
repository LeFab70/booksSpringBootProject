package org.example.books.books.services.implement;

import lombok.RequiredArgsConstructor;
import org.example.books.authors.entities.AuthorEntity;
import org.example.books.authors.repositories.AuthorRepository;
import org.example.books.books.dto.dto.BookRequest;
import org.example.books.books.dto.dto.BookResponse;
import org.example.books.books.entities.BooksEntity;
import org.example.books.exceptions.BadRequestException;
import org.example.books.exceptions.ResourceNotFoundException;
import org.example.books.books.repositories.BooksRepository;
import org.example.books.books.services.interfaces.BooksServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServicesImplement implements BooksServices {

    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;
    // Constructor injection is handled by @RequiredArgsConstructor
//    public BooksServicesImplement(BooksRepository booksRepository, AuthorRepository authorRepository) {
//        this.booksRepository = booksRepository;
//        this.authorRepository = authorRepository;
//    }

    @Override
    public ResponseEntity<String> addBook(BookRequest request) {

        if (request == null)
            throw new BadRequestException("Book request cannot be null.");
          //verifies if author exists
        AuthorEntity author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found."));

        //verifies if book already exists
        boolean isExistingBook = booksRepository.existsByTitleAndAuthorAndPages(
                request.title(),
                author,
                request.pages()
        );
        if (isExistingBook)
            throw new BadRequestException("Book already exists in the database.");

            //continue to add the book
        BooksEntity book = BooksEntity.builder()
                    .title(request.title())
                    .author(author)
                    .pages(request.pages())
                    .publishedDate(request.publishedDate())
                    .build();
            booksRepository.save(book);
            return ResponseEntity.ok("Book added successfully.");
    }

    @Override
    public ResponseEntity<String> removeBook(Long id) {
        return null;
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return booksRepository.findAll().stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getPages(),
                        book.getPublishedDate(),
                        book.getAuthor().getFirst_name()+" "+book.getAuthor().getLast_name(),
                        Period.between(book.getAuthor().getBirth_date(), LocalDate.now()) .getYears()
                ))
                .toList();
    }

    @Override
    public BookResponse getBookById(Long id) {
        boolean exists = booksRepository.existsById(id);
        if (exists) {
            BooksEntity book = booksRepository.findById(id).get();
            return new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getPages(),
                    book.getPublishedDate(),
                    book.getAuthor().getFirst_name()+" "+book.getAuthor().getLast_name(),
                    Period.between(book.getAuthor().getBirth_date(), LocalDate.now()) .getYears()
            );
        }
        else
            throw new ResourceNotFoundException("Book not found.");
    }

    @Override
    public ResponseEntity<String> updateBook(Long id, BookRequest request) {

        boolean exists = booksRepository.existsById(id);
        if (exists) {
            BooksEntity bookToUpdate = booksRepository.findById(id).get();

            //verifies if author exists
            AuthorEntity author = authorRepository.findById(request.authorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found."));

            bookToUpdate.setTitle(request.title());
            bookToUpdate.setPages(request.pages());
            bookToUpdate.setPublishedDate(request.publishedDate());
            bookToUpdate.setAuthor(author);

            booksRepository.save(bookToUpdate);
            return ResponseEntity.ok("Book updated successfully.");
        } else {
            throw new ResourceNotFoundException("Book not found.");
        }

    }
}
