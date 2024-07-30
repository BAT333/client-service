package com.example.service.client.controller;


import com.example.service.client.model.client.DataClient;
import com.example.service.client.model.client.DataClientDTO;
import com.example.service.client.model.client.DataClientUpdateDTO;
import com.example.service.client.service.ClientService;
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
@RequestMapping("/proprietor")
@SecurityRequirement(name = "bearer-key")
public class ClientController {
    @Autowired
    private ClientService service;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataClient> register(@RequestBody @Valid DataClientDTO dto, UriComponentsBuilder builder){
        var client = this.service.registerClient(dto);
        var uri = builder.path("/client/{id}").buildAndExpand(client.id()).toUri();
        return ResponseEntity.created(uri).body(client);
    }
    @GetMapping
    public ResponseEntity<Page<DataClient>> listAll(@PageableDefault(sort = {"id"}) Pageable pageable){
        return ResponseEntity.ok(this.service.listClient(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<DataClient> list(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.service.proprietor(id));
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DataClientDTO> update (@RequestBody @Valid DataClientUpdateDTO dto, @PathVariable("id") Long id){
        var proprietor = this.service.updateClient(dto,id);
        return ResponseEntity.ok(proprietor);

    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id") Long id){
        this.service.deleteProprietor(id);
        return ResponseEntity.noContent().build();
    }
}
