package com.example.service.client.service;


import com.example.service.client.config.exceptions.ClientException;
import com.example.service.client.domain.Client.Client;
import com.example.service.client.domain.User;
import com.example.service.client.domain.UserRole;
import com.example.service.client.model.client.DataClient;
import com.example.service.client.model.client.DataClientDTO;
import com.example.service.client.model.client.DataClientUpdateDTO;
import com.example.service.client.repository.ClientRepository;
import com.example.service.client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;
    @Autowired
    private UserRepository userRepository;
    public DataClient registerClient(DataClientDTO dto) {
        if(repository.existsByCpf(dto.cpf())||repository.existsByEmail(dto.email())){
            throw new ClientException();
        }
        var user =  userRepository.save(new User(dto.email(),this.encoder(dto.birth()), UserRole.SUB));

        var client = repository.save(new Client(dto,user));
        //fazer validação, e passar para microserviço

        return new DataClient(client);
    }

    private String encoder(LocalDate birth) {
        return new BCryptPasswordEncoder().encode(birth.toString());
    }

    public Page<DataClient> listClient(Pageable pageable) {
        return this.repository.findByActiveTrue(pageable).map(DataClient::new);
    }

    public DataClientDTO updateClient(DataClientUpdateDTO dto, Long id) {
        if(!repository.existsByIdAndActiveTrue(id)||repository.existsByEmail(dto.email())){
            throw new ClientException();
        }
        var proprietor = this.repository.findByIdAndActiveTrue(id);
        if(proprietor.isPresent()){
            proprietor.get().update(dto);
            return new DataClientDTO(proprietor.get());
        }
        return null;
    }

    public void deleteProprietor(Long id) {
        if(!repository.existsByIdAndActiveTrue(id)){
            throw new ClientException();
        }
        var client = this.repository.findByIdAndActiveTrue(id);
        client.ifPresent(Client::delete);
    }

    public DataClient proprietor(Long id) {
        if(!repository.existsByIdAndActiveTrue(id)){
            throw new ClientException();
        }
        return this.repository.findByIdAndActiveTrue(id).map(DataClient::new).orElse(null);
    }
}
