package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
public class Nastavnik extends User {

    @Size(min = 13, max = 13, message = "JMBG mora imati 13 karaktera")
    //@Pattern(regexp = "^[0-9]{13}$", message = "JMBG mora da se sastoji samo od brojeva")
    @Column(nullable = false, unique = true)
    String jmbg;

    String adresa;

}
