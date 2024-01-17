package com.example.eobrazovanje.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IspitniRokDto {
    Long id = 0L;
    PredajeDto predaje;
    RokDto rok;
    LocalDate datumIspita;
}
