package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Predaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "nastavnik_id", nullable = false)
    Nastavnik nastavnik;

    @ManyToOne
    @JoinColumn(name = "predmet_id", nullable = false)
    Predmet predmet;

    @ManyToOne
    @JoinColumn(name = "zvanje_id", nullable = false)
    Zvanje zvanje;
}
