package com.example.eobrazovanje.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UplataDto {
    Long id = 0L;
    LocalDate datum;
    Double iznos;
    StudentDto student;
    Boolean stornirano;

}
