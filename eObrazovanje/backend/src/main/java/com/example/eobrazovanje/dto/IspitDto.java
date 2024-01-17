package com.example.eobrazovanje.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IspitDto {

    Long id = 0L;
    LocalDate datum;
    PredajeDto predaje;
    StudentDto student;
    Integer bodovi;
    Boolean konacno;


}
