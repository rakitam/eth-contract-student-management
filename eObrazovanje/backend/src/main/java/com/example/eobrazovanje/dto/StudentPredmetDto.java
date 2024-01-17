package com.example.eobrazovanje.dto;

import lombok.Data;

@Data
public class StudentPredmetDto {
    Long id = 0L;
    PredmetDto predmet;
    StudentDto student;
    boolean polozio;
    Integer osvojeniBodovi;
}
