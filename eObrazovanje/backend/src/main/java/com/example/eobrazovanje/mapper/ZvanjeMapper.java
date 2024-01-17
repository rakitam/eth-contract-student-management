package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.ZvanjeDto;
import com.example.eobrazovanje.model.Zvanje;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ZvanjeMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Zvanje toEntity(ZvanjeDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Zvanje.class);
    }

    public ZvanjeDto toDto(Zvanje entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, ZvanjeDto.class);
    }

    public List<Zvanje> toEntity(Collection<ZvanjeDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<ZvanjeDto> toDto(Collection<Zvanje> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
