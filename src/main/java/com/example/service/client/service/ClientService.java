package com.example.service.client.service;


import com.example.service.client.config.exceptions.ClientException;
import com.example.service.client.domain.Client.Client;
import com.example.service.client.model.client.DataClient;
import com.example.service.client.model.client.DataClientDTO;
import com.example.service.client.model.client.DataClientUpdateDTO;
import com.example.service.client.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public DataClient registerClient(DataClientDTO dto) {
        log.info("Registering a new customer");
        if(repository.existsByCpf(dto.cpf())||repository.existsByEmail(dto.email())){
            log.error("error registering new customer");
            throw new ClientException();
        }
        //registrar um novo user
        // var user =  userRepository.save(new User(dto.email(),this.encoder(dto.birth()), UserRole.SUB));

        var client = repository.save(new Client(dto,null));
        //fazer validação, e passar para microserviço

        return new DataClient(client);
    }

    public Page<DataClient> listClient(Pageable pageable) {
        log.info("listing all customers");
        return this.repository.findByActiveTrue(pageable).map(DataClient::new);
    }

    public DataClientDTO updateClient(DataClientUpdateDTO dto, Long id) {
        log.info("update customer information : {}", id);
        if(!repository.existsByIdAndActiveTrue(id)||repository.existsByEmail(dto.email())){
            log.error("error updating customer information: {}", id);
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
        log.info("Deleting client: {}", id);
        if(!repository.existsByIdAndActiveTrue(id)){
            log.error("error Deleting customer : {}", id);
            throw new ClientException();
        }
        var client = this.repository.findByIdAndActiveTrue(id);
        client.ifPresent(Client::delete);
    }

    public DataClient proprietor(Long id) {
        log.info("listing customer");
        if(!repository.existsByIdAndActiveTrue(id)){
            log.info("Deleting listing customer");
            throw new ClientException();
        }
        return this.repository.findByIdAndActiveTrue(id).map(DataClient::new).orElse(null);
    }
}
