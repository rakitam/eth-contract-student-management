package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Rok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String naziv;

    @Column(nullable = false)
    LocalDate pocetak;

    @Column(nullable = false)
    LocalDate kraj;

}
