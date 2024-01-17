package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class StudentPredmet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "predmet_id", nullable = false)
    Predmet predmet;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @Column(nullable = false)
    boolean polozio;

    @Column(nullable = false)
    Integer osvojeniBodovi;
}
