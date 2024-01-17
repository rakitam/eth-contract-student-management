package com.example.eobrazovanje.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
public class Student extends User {

    @Size(min = 13, max = 13, message = "JMBG mora imati 13 karaktera")
    //@Pattern(regexp = "^[0-9]{13}$", message = "JMBG mora da se sastoji samo od brojeva")
    @JoinColumn(nullable = false)
    String jmbg;

    String adresa;

    @Column(nullable = false)
    //@Pattern(regexp = "^[A-Z]{2}-[1-9][0-9]?-2(00[0-9]|0[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "Format broja indeksa nije validan")
    String brojIndeksa;

    //@Pattern(regexp = "^[0-9]{3}-[0-9]{13}-[0-9]{2}$", message = "Morate uneti ispravan broj bankovnog računa.")
    @JoinColumn(nullable = false)
    String tekuciRacun;

    Double stanje;

    @Size(min = 9, max = 10, message = "Pogrešan broj telefona")
    String brojTelefona;

}
