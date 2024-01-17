package com.example.eobrazovanje.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrijavaDto {
    Long id = 0L;
    LocalDate datumPrijave;
    StudentDto student;
    IspitDto ispit;
    IspitniRokDto ispitniRok;

}
