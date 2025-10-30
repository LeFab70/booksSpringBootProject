package org.leFab.library.adress.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.leFab.library.authors.entities.AuthorEntity;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;
    @Column(nullable = false, length = 50)
    private String street;
    @Column(nullable = false, length = 50)
    private String city;
    @Column(nullable = false, length = 50)
    private String state;
    @Column(unique = true, nullable = false, length = 10)
    private String zipCode;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<AuthorEntity> authors;
}
