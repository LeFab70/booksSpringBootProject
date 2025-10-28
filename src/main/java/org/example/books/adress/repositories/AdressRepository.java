package org.example.books.adress.repositories;

import org.example.books.adress.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends JpaRepository<AddressEntity, Long> {
}
