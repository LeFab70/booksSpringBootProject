package org.leFab.library.books.services.implement;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.leFab.library.adress.entities.AddressEntity;
import org.leFab.library.adress.repositories.AdressRepository;
import org.leFab.library.authors.entities.AuthorEntity;
import org.leFab.library.authors.repositories.AuthorRepository;
import org.leFab.library.books.dto.BookRequest;
import org.leFab.library.books.dto.BookResponse;
import org.leFab.library.books.entities.BooksEntity;
import org.leFab.library.exceptions.BadRequestException;
import org.leFab.library.exceptions.ResourceAlreadyExist;
import org.leFab.library.exceptions.ResourceNotFoundException;
import org.leFab.library.books.repositories.BooksRepository;
import org.leFab.library.books.services.interfaces.BooksServices;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServicesImplement implements BooksServices {
    private final AdressRepository adressRepository;
    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;
    // Constructor injection is handled by @RequiredArgsConstructor
//    public BooksServicesImplement(BooksRepository booksRepository, AuthorRepository authorRepository) {
//        this.booksRepository = booksRepository;
//        this.authorRepository = authorRepository;
//    }

    @Override
    public BookResponse addBook(BookRequest request) {

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
        throw new ResourceAlreadyExist("Book already exists in the database.");

    BooksEntity book = BooksEntity.builder()
                    .title(request.title())
                    .author(author)
                    .pages(request.pages())
                    .publishedDate(request.publishedDate())
                    .build();
            booksRepository.save(book);
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getPages(),
                book.getPublishedDate(),
                book.getAuthor().getFirstName()+" "+book.getAuthor().getLastName(),
                Period.between(book.getAuthor().getBirthDate(), LocalDate.now()) .getYears()
        );
    }

    @Override
    public void removeBook(Long id) {

        if(id==null)
            throw new BadRequestException("Book id cannot be null.");
        BooksEntity book=booksRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found."));
        booksRepository.delete(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return booksRepository.findAll().stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getPages(),
                        book.getPublishedDate(),
                        book.getAuthor().getFirstName()+" "+book.getAuthor().getLastName(),
                        Period.between(book.getAuthor().getBirthDate(), LocalDate.now()) .getYears()
                ))
                .toList();
    }

    @Override
    public BookResponse getBookById(Long id) {

        if(id==null)
            throw new BadRequestException("Book id cannot be null.");
        BooksEntity book=booksRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found."));
        return new BookResponse(
                      book.getId(),
                  book.getTitle(),
                   book.getPages(),
                   book.getPublishedDate(),
                   book.getAuthor().getFirstName()+" "+book.getAuthor().getLastName(),
                    Period.between(book.getAuthor().getBirthDate(), LocalDate.now()) .getYears()
                );

       // boolean exists = booksRepository.existsById(id);
//        if (exists) {
//            Optional<BooksEntity> bookOp = booksRepository.findById(id);
//            if(bookOp.isPresent())
//            {
//                BooksEntity book=bookOp.get();
//                return new BookResponse(
//                    book.getId(),
//                    book.getTitle(),
//                    book.getPages(),
//                    book.getPublishedDate(),
//                    book.getAuthor().getFirstName()+" "+book.getAuthor().getLastName(),
//                    Period.between(book.getAuthor().getBirthDate(), LocalDate.now()) .getYears()
//            );
//            }
//            else throw new ResourceNotFoundException("Book not found.");
//        }
//        else
//            throw new ResourceNotFoundException("Book not found.");
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {

//        boolean exists = booksRepository.existsById(id);
//        if (exists) {
//            BooksEntity bookToUpdate = booksRepository.findById(id).get();

            // si le livre nest pas trouvé exception levée ici
           BooksEntity bookToUpdate=booksRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found."));

            //verifies if author exists
            AuthorEntity author = authorRepository.findById(request.authorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found."));

            //verifier si le livre est deja en base
            boolean isExistingBook = booksRepository.existsByTitleAndAuthorAndPages(request.title(), author, request.pages());
    if (isExistingBook)
        throw new ResourceAlreadyExist("Book already exists in the database.");

    bookToUpdate.setTitle(request.title());
            bookToUpdate.setPages(request.pages());
            bookToUpdate.setPublishedDate(request.publishedDate());
            bookToUpdate.setAuthor(author);

           BooksEntity book= booksRepository.save(bookToUpdate);
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getPages(),
                book.getPublishedDate(),
                book.getAuthor().getFirstName()+" "+book.getAuthor().getLastName(),
                Period.between(book.getAuthor().getBirthDate(), LocalDate.now()) .getYears()
        );
    }
@Override
public void generateTestData(){
        Faker faker = new Faker();
        LocalDate start = LocalDate.of(1950, 1, 1);
        LocalDate end = LocalDate.of(2005, 12, 31);

        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = startEpochDay + faker.number().numberBetween(0, (endEpochDay - startEpochDay));

        if (getAllBooks().isEmpty()) {
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
                                .firstName(author)
                                .lastName(faker.name().lastName())
                                .birthDate(LocalDate.ofEpochDay(startEpochDay + faker.number().numberBetween(0, (endEpochDay - startEpochDay))))
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
                addBook(bookRequest);
            }
        }
        else {
            throw new BadRequestException("Books already exist in the database. No test data added.");
        }
    }
}
