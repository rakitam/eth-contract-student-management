package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.StudentPredmetDto;
import com.example.eobrazovanje.model.StudentPredmet;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentPredmetMapper {

    ModelMapper modelMapper = new ModelMapper();

    public StudentPredmet toEntity(StudentPredmetDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, StudentPredmet.class);
    }

    public StudentPredmetDto toDto(StudentPredmet entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, StudentPredmetDto.class);
    }

    public List<StudentPredmet> toEntity(Collection<StudentPredmetDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<StudentPredmetDto> toDto(Collection<StudentPredmet> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
