package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    //@Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]+$", message = "Korisničko ime mora da sadrži barem jedno slovo.")
    String username;

    //@Column(nullable = false)
    String password;

    String ime;

    String prezime;

    @Enumerated(EnumType.STRING)
    Role role;
}
