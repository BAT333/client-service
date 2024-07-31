package com.example.service.client.model.client;


import com.example.service.client.domain.Client.Client;

public record DataClient(
        Long id,
        String name,
        String email
) {
    public DataClient(Client client) {
        this(client.getId(),client.getName(),client.getEmail());
    }
}
