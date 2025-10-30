package org.leFab.library.authors.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record AuthorRequest(
    String firstName,
    @NotNull(message = "Last name must not be null")
    @NotBlank(message = "Last name must not be blank")
    @Length(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    String lastName,
    @NotNull(message = "Birth date must not be null")
    @Past(message = "Birth date must be in the past")
    LocalDate birthDate,
    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email must not be null")
    @Pattern(regexp = "^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$",
            message = "Email format is invalid")
    String email,
    @NotBlank(message = "Address ID must not be blank")
    @NotNull(message = "Address ID must not be null")
    String addressId

) {
}
