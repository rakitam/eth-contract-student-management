package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PredispitnaObaveza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String naziv;

    @Column(nullable = false)
    Integer minBodova;

    @ManyToOne
    @JoinColumn(name = "predmet_id", nullable = false)
    Predmet predmet;

    @Enumerated(EnumType.STRING)
    TipPredispitneObaveze tip;
}
