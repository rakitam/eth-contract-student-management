package com.example.eobrazovanje.dto;

import lombok.Data;

@Data
public class DokumentDto {

    Long id = 0L;
    String naziv;
    String url;
    StudentDto student;
}
