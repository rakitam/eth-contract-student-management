package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class IspitniRok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    LocalDate datumIspita;

    @ManyToOne
    @JoinColumn(name = "predaje_id", nullable = false)
    Predaje predaje;

    @ManyToOne
    @JoinColumn(name = "rok_id", nullable = false)
    Rok rok;
}
