package org.example.books.adress.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.books.authors.entities.AuthorEntity;

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
    private String street;
    private String city;
    private String state;
    @Column(unique = true, nullable = false)
    private String zipCode;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<AuthorEntity> authors;
}
