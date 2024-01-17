package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.PrijavaDto;
import com.example.eobrazovanje.model.Prijava;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrijavaMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Prijava toEntity(PrijavaDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Prijava.class);
    }

    public PrijavaDto toDto(Prijava entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, PrijavaDto.class);
    }

    public List<Prijava> toEntity(Collection<PrijavaDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<PrijavaDto> toDto(Collection<Prijava> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
