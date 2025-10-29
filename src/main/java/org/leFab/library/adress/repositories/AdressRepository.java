package org.leFab.library.adress.repositories;

import org.leFab.library.adress.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends JpaRepository<AddressEntity, Long> {
}
