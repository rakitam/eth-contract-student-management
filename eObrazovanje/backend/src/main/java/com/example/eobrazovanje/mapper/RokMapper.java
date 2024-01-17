package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.RokDto;
import com.example.eobrazovanje.model.Rok;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RokMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Rok toEntity(RokDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Rok.class);
    }

    public RokDto toDto(Rok entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, RokDto.class);
    }

    public List<Rok> toEntity(Collection<RokDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<RokDto> toDto(Collection<Rok> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
