package com.example.eobrazovanje.dto;

import lombok.Data;

@Data
public class PredajeDto {
    Long id = 0L;
    NastavnikDto nastavnik;
    PredmetDto predmet;
    ZvanjeDto zvanje;
}
