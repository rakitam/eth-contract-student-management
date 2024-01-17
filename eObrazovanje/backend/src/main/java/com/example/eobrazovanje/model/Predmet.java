package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Predmet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String naziv;

    @Column(nullable = false)
    String oznaka;

    @Column(nullable = false)
    Integer espb;


}
