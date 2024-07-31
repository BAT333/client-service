package com.example.service.client.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DataLoginDTO(
        @NotNull
        @Email
        String login,
        @NotNull
        @Pattern(regexp = "([a-zA-z0-9\\W]){8,255}")
        String password

) {
}
