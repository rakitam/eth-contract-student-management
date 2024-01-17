package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.PredmetDto;
import com.example.eobrazovanje.model.Predmet;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PredmetMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Predmet toEntity(PredmetDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Predmet.class);
    }

    public PredmetDto toDto(Predmet entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, PredmetDto.class);
    }

    public List<Predmet> toEntity(Collection<PredmetDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<PredmetDto> toDto(Collection<Predmet> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
