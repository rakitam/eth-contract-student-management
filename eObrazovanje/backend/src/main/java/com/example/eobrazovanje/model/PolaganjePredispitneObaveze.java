package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class PolaganjePredispitneObaveze {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    LocalDate datum;

    @Column(nullable = false)
    Double bodovi;

    @Column(nullable = false)
    boolean polozio;

    @ManyToOne
    @JoinColumn(name = "predispitna_obaveza_id", nullable = false)
    PredispitnaObaveza predispitnaObaveza;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;
}
