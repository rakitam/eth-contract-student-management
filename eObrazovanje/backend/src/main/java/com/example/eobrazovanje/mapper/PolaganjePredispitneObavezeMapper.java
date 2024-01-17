package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.PolaganjePredispitneObavezeDto;
import com.example.eobrazovanje.model.PolaganjePredispitneObaveze;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PolaganjePredispitneObavezeMapper {

    ModelMapper modelMapper = new ModelMapper();

    public PolaganjePredispitneObaveze toEntity(PolaganjePredispitneObavezeDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, PolaganjePredispitneObaveze.class);
    }

    public PolaganjePredispitneObavezeDto toDto(PolaganjePredispitneObaveze entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, PolaganjePredispitneObavezeDto.class);
    }

    public List<PolaganjePredispitneObaveze> toEntity(Collection<PolaganjePredispitneObavezeDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<PolaganjePredispitneObavezeDto> toDto(Collection<PolaganjePredispitneObaveze> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
