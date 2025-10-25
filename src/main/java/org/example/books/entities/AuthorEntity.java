package org.example.books.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "author")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "author_id")
    private  Long id;
    private String first_name;
    private String last_name;
    private LocalDate birth_date;
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "author",cascade = jakarta.persistence.CascadeType.ALL)
    private List<BooksEntity> books;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ADDRESS_AUTHOR", foreignKeyDefinition = "FOREIGN KEY (address_id) REFERENCES address(address_id) ON DELETE CASCADE"))
    private AddressEntity address;

}
