package org.example.books.services.implement;

import org.example.books.entities.BooksEntity;
import org.example.books.repositories.BooksRepository;
import org.example.books.services.interfaces.BooksServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServicesImplement implements BooksServices {

    private final BooksRepository booksRepository;

    public BooksServicesImplement(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public void addBook(String title, String author, Integer pages) {
        BooksEntity book = BooksEntity.builder()
                .title(title)
                .author(author)
                .pages(pages)
                .build();
        booksRepository.save(book);
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
