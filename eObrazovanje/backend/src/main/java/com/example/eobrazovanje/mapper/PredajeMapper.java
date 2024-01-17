package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.PredajeDto;
import com.example.eobrazovanje.model.Predaje;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PredajeMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Predaje toEntity(PredajeDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Predaje.class);
    }

    public PredajeDto toDto(Predaje entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, PredajeDto.class);
    }

    public List<Predaje> toEntity(Collection<PredajeDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<PredajeDto> toDto(Collection<Predaje> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
