package org.example.books.authors.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AuthorRequest(
    String firstName,
    @NotNull(message = "Last name must not be null")
    @NotBlank(message = "Last name must not be blank")
    String lastName,
    @NotNull(message = "Birth date must not be null")
    @Past(message = "Birth date must be in the past")
    LocalDate birthDate,
    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email must not be null")
    String email,
    @NotBlank(message = "Address ID must not be blank")
    @NotNull(message = "Address ID must not be null")
    String addressId

) {
}
