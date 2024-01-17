package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.IspitDto;
import com.example.eobrazovanje.model.Ispit;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IspitMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Ispit toEntity(IspitDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Ispit.class);
    }

    public IspitDto toDto(Ispit entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, IspitDto.class);
    }

    public List<Ispit> toEntity(Collection<IspitDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<IspitDto> toDto(Collection<Ispit> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
