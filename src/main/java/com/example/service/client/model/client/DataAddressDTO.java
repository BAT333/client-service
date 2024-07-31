package com.example.service.client.model.client;


import com.example.service.client.domain.Address;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DataAddressDTO(
        @NotNull
        String street,
        @NotNull
        String state,
        @NotNull
        String neighborhood,
        @NotNull
        @Pattern(regexp ="[0-9]{1,3}")
        String number,
        @Pattern(regexp ="[0-9]{8}")
        String cep,
        String complemento
) {
    public DataAddressDTO(Address address) {
        this(address.getStreet(), address.getState(), address.getNeighborhood(), address.getNumber(), address.getCep(), address.getComplemento());
    }
}
