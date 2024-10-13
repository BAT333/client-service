package com.example.service.client.model.client;


import com.example.service.client.domain.Client.Client;

import java.io.Serializable;

public record DataClient(
        Long id,
        String name,
        String email
)implements Serializable {
    public DataClient(Client client) {
        this(client.getId(),client.getName(),client.getEmail());
    }
}
