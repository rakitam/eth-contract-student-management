package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.UplataDto;
import com.example.eobrazovanje.model.Uplata;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UplataMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Uplata toEntity(UplataDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Uplata.class);
    }

    public UplataDto toDto(Uplata entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, UplataDto.class);
    }

    public List<Uplata> toEntity(Collection<UplataDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<UplataDto> toDto(Collection<Uplata> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
