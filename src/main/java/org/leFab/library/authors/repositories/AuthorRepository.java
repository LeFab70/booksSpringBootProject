package org.leFab.library.authors.repositories;

import lombok.NoArgsConstructor;
import org.leFab.library.adress.repositories.AdressRepository;
import org.leFab.library.authors.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {

    public AuthorEntity findByFirstNameAndLastName(String firstName, String lastName);
    public void deleteByFirstNameAndLastName(String firstName, String lastName);

    //query native SQL
     @Query(value = "SELECT * FROM authors WHERE first_name = ?1 AND last_name = ?2", nativeQuery = true)
    public AuthorEntity findByFirstNameAndLastNameNativeQuery(@Param("firstName") String firstName, @Param("lastName") String lastName);

     //liste des clients d'une adresse
     @Query("SELECT a FROM AuthorEntity a WHERE a.address = :address")
     public Iterable<AuthorEntity> findByAddressNative(@Param("address") AdressRepository address);

     //avec jpa query
    public AuthorEntity findByAddress(AdressRepository address);
}
