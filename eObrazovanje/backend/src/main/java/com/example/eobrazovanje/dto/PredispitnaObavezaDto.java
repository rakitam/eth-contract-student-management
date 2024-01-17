package com.example.eobrazovanje.dto;

import com.example.eobrazovanje.model.TipPredispitneObaveze;
import lombok.Data;

@Data
public class PredispitnaObavezaDto {
    Long id = 0L;
    String naziv;
    Integer minBodova;
    PredmetDto predmet;
    TipPredispitneObaveze tip;
}
