package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Prijava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    LocalDate datumPrijave;

    @ManyToOne
    @JoinColumn(nullable = false)
    Student student;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ispit_id", nullable = false)
    Ispit ispit;

    @ManyToOne
    @JoinColumn(name = "ispitni_rok_id", nullable = false)
    IspitniRok ispitniRok;


}
