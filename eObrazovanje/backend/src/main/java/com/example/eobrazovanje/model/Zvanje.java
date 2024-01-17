package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Zvanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String naziv;


}
