package org.leFab.library.authors.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.leFab.library.adress.entities.AddressEntity;
import org.leFab.library.books.entities.BooksEntity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private  Long id;
    @Column( nullable = false)
    private String first_name;
    private String last_name;
    @Column(nullable = false)
    private LocalDate birth_date;
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<BooksEntity> books;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ADDRESS_AUTHOR", foreignKeyDefinition = "FOREIGN KEY (address_id) REFERENCES address(address_id) ON DELETE CASCADE"))
    private AddressEntity address;

}
