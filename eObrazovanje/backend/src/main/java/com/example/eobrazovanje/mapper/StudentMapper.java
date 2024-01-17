package com.example.eobrazovanje.mapper;

import com.example.eobrazovanje.dto.StudentDto;
import com.example.eobrazovanje.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Student toEntity(StudentDto dto) {
        if(dto == null) {
            return null;
        }
        return modelMapper.map(dto, Student.class);
    }

    public StudentDto toDto(Student entity) {
        if(entity == null) {
            return null;
        }
        return modelMapper.map(entity, StudentDto.class);
    }

    public List<Student> toEntity(Collection<StudentDto> dtoList) {
        return dtoList.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }

    public List<StudentDto> toDto(Collection<Student> entityList) {
        return entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}
