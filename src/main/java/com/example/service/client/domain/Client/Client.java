package com.example.service.client.domain.Client;


import com.example.service.client.domain.Address;
import com.example.service.client.model.client.DataClientDTO;
import com.example.service.client.model.client.DataClientUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "names",nullable = false)
    private String name;
    @Column(name = "emails",nullable = false,unique = true)
    private String email;
    @Column(name = "births",nullable = false)
    private LocalDate birth;
    @Column(name = "cpfs",nullable = false,unique = true)
    private String cpf;
    @Column(name ="actives",nullable = false)
    private Boolean active = true;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",unique = true,nullable = false)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logins",nullable = false,unique = true)
    private String login;


    public Client(DataClientDTO dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.cpf = dto.cpf();
        this.birth = dto.birth();
        this.active = true;
        this.address = new Address(dto.address());
    }

    public Client(DataClientDTO dto, String user) {
        this.name = dto.name();
        this.email = dto.email();
        this.cpf = dto.cpf();
        this.birth = dto.birth();
        this.active = true;
        this.address = new Address(dto.address());
        this.login = user;

    }

    public void update(DataClientUpdateDTO dto) {
        if(dto.name() != null){this.name = dto.name();}
        if(dto.birth() != null){this.birth = dto.birth();}
        if(dto.email() != null){this.email = dto.email();}
        if(dto.address() != null){this.address.update(dto.address());}

    }

    public void delete() {
        this.active = false;
        this.address.delete();
         //this.login.delete();
        //deletar login
       //deletar propriedades
    }
}
