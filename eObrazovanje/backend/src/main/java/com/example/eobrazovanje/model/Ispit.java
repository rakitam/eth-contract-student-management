package com.example.eobrazovanje.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Ispit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    LocalDate datum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predaje_id", nullable = false)
    Predaje predaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @Column(nullable = false)
    Integer bodovi;

    @Column(nullable = false)
    Boolean konacno;

    @OneToOne(mappedBy = "ispit", optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "prijava_id", nullable = false)
    Prijava prijava;

}
