package com.example.eobrazovanje.dto;

import com.example.eobrazovanje.model.Role;
import lombok.Data;

@Data
public class StudentDto {
    Long id = 0L;
    String username;
    String ime;
    String prezime;
    Role role;
    String jmbg;
    String adresa;
    String brojIndeksa;
    String tekuciRacun;
    Double stanje;
    String brojTelefona;
}
