package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.PredispitnaObavezaDto;
import com.example.eobrazovanje.model.PredispitnaObaveza;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PredispitnaObavezaMapper {

    ModelMapper modelMapper = new ModelMapper();

    public PredispitnaObaveza toEntity(PredispitnaObavezaDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, PredispitnaObaveza.class);
    }

    public PredispitnaObavezaDto toDto(PredispitnaObaveza entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, PredispitnaObavezaDto.class);
    }

    public List<PredispitnaObaveza> toEntity(Collection<PredispitnaObavezaDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<PredispitnaObavezaDto> toDto(Collection<PredispitnaObaveza> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
