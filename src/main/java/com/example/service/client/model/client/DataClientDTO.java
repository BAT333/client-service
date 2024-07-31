package com.example.service.client.model.client;

import com.example.service.client.domain.Client.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record DataClientDTO(
        @NotNull
        @Pattern(regexp ="[a-zA-Z]{3,255}")
        String name,
        @NotNull
        @Email
        String email,
        @Past
        LocalDate birth,
        @CPF
        @NotNull
        String cpf,
        @Valid
        DataAddressDTO address
) {
    public DataClientDTO(Client client) {
        this(client.getName(), client.getEmail(),client.getBirth(), client.getCpf(), new DataAddressDTO(client.getAddress()));
    }
}
