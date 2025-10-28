package org.example.books.books.repositories;

import org.example.books.authors.entities.AuthorEntity;
import org.example.books.books.entities.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Long> {
    //public List<BooksEntity> findByTitleAndAuthorAndPages(String title, AuthorEntity author, Integer pages);

    //join query looking for books by title, author's first name, and number of pages
    //public List<BooksEntity> findByTitleAndAuthor_FirstNameAndPages(String title, String firstName, Integer pages);

    //join query looking for books by title, author's id, and number of pages
    //public List<BooksEntity> findByTitleAndAuthor_IdAndPages(String title, Long authorId, Integer pages);

    //check if a book exists by title, author, and number of pages
    public boolean existsByTitleAndAuthorAndPages(String title, AuthorEntity author, Integer pages);
}
