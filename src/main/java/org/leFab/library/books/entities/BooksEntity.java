package org.leFab.library.books.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.leFab.library.authors.entities.AuthorEntity;

import java.time.LocalDate;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Getter
//@Setter
@Data
public class BooksEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    private String title;
    private Integer pages;
    private LocalDate publishedDate;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "FK_AUTHOR_BOOKS", foreignKeyDefinition = "FOREIGN KEY (author_id) REFERENCES author(author_id) ON DELETE CASCADE"))
    @JsonManagedReference // to handle bidirectional relationship during JSON serialization
    private AuthorEntity author;
}
