package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Ispit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "predaje_id", nullable = false)
    Predaje predaje;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @Column(nullable = false)
    Integer bodovi;

    @Column(nullable = false)
    Boolean konacno;

    @OneToOne(mappedBy = "ispit", optional = false)
    @JoinColumn(name = "prijava_id", nullable = false)
    Prijava prijava;

}
