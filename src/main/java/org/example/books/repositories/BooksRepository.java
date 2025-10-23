package org.example.books.repositories;

import org.example.books.entities.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Long> {
}
