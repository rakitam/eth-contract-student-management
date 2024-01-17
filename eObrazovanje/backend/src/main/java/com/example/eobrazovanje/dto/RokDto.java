package com.example.eobrazovanje.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RokDto {
    Long id = 0L;
    String naziv;
    LocalDate pocetak;
    LocalDate kraj;

}
