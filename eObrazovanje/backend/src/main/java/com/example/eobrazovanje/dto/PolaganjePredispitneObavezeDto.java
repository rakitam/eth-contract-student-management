package com.example.eobrazovanje.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PolaganjePredispitneObavezeDto {
    Long id = 0L;
    LocalDate datum;
    Double bodovi;
    boolean polozio;
    PredispitnaObavezaDto predispitnaObaveza;
    StudentDto student;
}
