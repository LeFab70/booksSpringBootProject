package org.example.books.adress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdressRequest(
        @NotBlank(message = "Street cannot be blank")
        @NotNull(message = "Street cannot be null")
        String street,
        @NotBlank(message = "City cannot be blank")
        @NotNull(message = "City cannot be null")
        String city,
        @NotBlank(message = "State cannot be blank")
        @NotNull(message = "State cannot be null")
        String state,
        @NotBlank(message = "Zip code cannot be blank")
        @NotNull(message = "Zip code cannot be null")
        String zipCode) {
}
