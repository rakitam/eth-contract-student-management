package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.IspitniRokDto;
import com.example.eobrazovanje.model.IspitniRok;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IspitniRokMapper {

    ModelMapper modelMapper = new ModelMapper();

    public IspitniRok toEntity(IspitniRokDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, IspitniRok.class);
    }

    public IspitniRokDto toDto(IspitniRok entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, IspitniRokDto.class);
    }

    public List<IspitniRok> toEntity(Collection<IspitniRokDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<IspitniRokDto> toDto(Collection<IspitniRok> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
