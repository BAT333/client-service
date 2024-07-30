package com.example.service.client.controller;

import com.example.service.client.config.TokenService;
import com.example.service.client.domain.User;
import com.example.service.client.domain.UserRole;
import com.example.service.client.model.DataLoginDTO;
import com.example.service.client.model.DataTokeDTO;
import com.example.service.client.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("auth")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataTokeDTO> login(@RequestBody @Valid DataLoginDTO dto){
        var user = new UsernamePasswordAuthenticationToken(dto.login(),dto.password());
        var userAuth = manager.authenticate(user);
        return ResponseEntity.ok(new DataTokeDTO(tokenService.generatesTokens((User) userAuth.getPrincipal())));
    }
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataLoginDTO> register(@RequestBody @Valid DataLoginDTO dto, UriComponentsBuilder builder){
        if(repository.findByLogin(dto.login()) != null)throw new RuntimeException();
        var user = repository.save(new User(dto.login(),this.encoder(dto.password()), UserRole.ADMIN));
        var uri = builder.path("auth/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new DataLoginDTO(user.getLogin(), user.getPasswords()));
    }

    private String encoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
