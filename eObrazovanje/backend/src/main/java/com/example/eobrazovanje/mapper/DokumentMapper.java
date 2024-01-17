package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.DokumentDto;
import com.example.eobrazovanje.model.Dokument;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DokumentMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Dokument toEntity(DokumentDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Dokument.class);
    }

    public DokumentDto toDto(Dokument entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, DokumentDto.class);
    }

    public List<Dokument> toEntity(Collection<DokumentDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<DokumentDto> toDto(Collection<Dokument> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
