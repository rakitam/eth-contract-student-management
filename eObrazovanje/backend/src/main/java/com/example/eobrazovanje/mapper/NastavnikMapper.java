package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.NastavnikDto;
import com.example.eobrazovanje.model.Nastavnik;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NastavnikMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Nastavnik toEntity(NastavnikDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Nastavnik.class);
    }

    public NastavnikDto toDto(Nastavnik entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, NastavnikDto.class);
    }

    public List<Nastavnik> toEntity(Collection<NastavnikDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<NastavnikDto> toDto(Collection<Nastavnik> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
