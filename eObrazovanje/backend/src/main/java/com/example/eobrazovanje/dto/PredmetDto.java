package com.example.eobrazovanje.dto;

import lombok.Data;

@Data
public class PredmetDto {
    Long id = 0L;
    String naziv;
    String oznaka;
    Integer espb;
}
