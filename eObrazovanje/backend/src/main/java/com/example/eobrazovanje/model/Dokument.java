package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Dokument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String naziv;

    @Column(nullable = false)
    String url;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;
}
