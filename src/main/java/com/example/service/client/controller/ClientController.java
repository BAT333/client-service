package com.example.service.client.controller;


import com.example.service.client.model.client.DataClient;
import com.example.service.client.model.client.DataClientDTO;
import com.example.service.client.model.client.DataClientUpdateDTO;
import com.example.service.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api/client/")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = {"http://172.27.64.1:8082", "http://localhost:8082"})
public class ClientController {
    @Autowired
    private ClientService service;

    @PostMapping("/register")
    @Transactional
    @Operation(summary ="Customer registration", description = "Requests customer information to register and then prompts to create user credentials")
    public ResponseEntity<DataClient> register(@RequestBody @Valid DataClientDTO dto, UriComponentsBuilder builder){
        var client = this.service.registerClient(dto);
        var uri = builder.path("/client/{id}").buildAndExpand(client.id()).toUri();
        return ResponseEntity.created(uri).body(client);
    }

    @GetMapping
    @Operation(summary ="List all customers", description = "Makes a list of all customers and returns the registered customers in order")
    public ResponseEntity<Page<DataClient>> listAll(@PageableDefault(sort = {"id"}) Pageable pageable){
        return ResponseEntity.ok(this.service.listClient(pageable));
    }

    @GetMapping("{id}")
    @Operation(summary ="Search for a specific customer", description ="Search for a specific customer by the customer's customer ID")
    public ResponseEntity<DataClient> list(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.service.proprietor(id));
    }
    @PutMapping("{id}")
    @Transactional
    @Operation(summary ="Update customer information", description ="Get the client by ID and make the necessary updates")
    public ResponseEntity<DataClientDTO> update (@RequestBody @Valid DataClientUpdateDTO dto, @PathVariable("id") Long id){
        var proprietor = this.service.updateClient(dto,id);
        return ResponseEntity.ok(proprietor);

    }
    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary ="Delete a client", description ="Get the client ID and delete it")
    public ResponseEntity delete(@PathVariable("id") Long id){
        this.service.deleteProprietor(id);
        return ResponseEntity.noContent().build();
    }
}
