package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.StudentPredmetDto;
import com.example.eobrazovanje.mapper.StudentPredmetMapper;
import com.example.eobrazovanje.model.StudentPredmet;
import com.example.eobrazovanje.service.StudentPredmetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student-predmet")
public class StudentPredmetController {

    @Autowired
    StudentPredmetService studentPredmetService;

    @Autowired
    StudentPredmetMapper studentPredmetMapper;

    @GetMapping
    public ResponseEntity<List<StudentPredmetDto>> getAll(Pageable pageable,
                                                          @RequestParam(defaultValue = "") String search,
                                                          @RequestParam(defaultValue = "0") Long predmetId,
                                                          @RequestParam(defaultValue = "0") Integer minBodova,
                                                          @RequestParam(defaultValue = "100") Integer maxBodova) {
        Page<StudentPredmet> studentPredmeti = studentPredmetService.getAll(pageable, search, minBodova, maxBodova, predmetId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(studentPredmeti.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(studentPredmeti.getTotalPages()));
        return new ResponseEntity<List<StudentPredmetDto>>(studentPredmetMapper.toDto(studentPredmeti.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentPredmetDto> getOne(@PathVariable Long id) {
        Optional<StudentPredmet> studentPredmet = studentPredmetService.getOne(id);
        if(!studentPredmet.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<StudentPredmetDto>(studentPredmetMapper.toDto(studentPredmet.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'NASTAVNIK')")
    public ResponseEntity<StudentPredmetDto> create(@RequestBody StudentPredmetDto studentPredmetDto) {
        StudentPredmet studentPredmet = studentPredmetService.save(studentPredmetMapper.toEntity(studentPredmetDto));
        return new ResponseEntity<StudentPredmetDto>(studentPredmetMapper.toDto(studentPredmet), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NASTAVNIK')")
    public ResponseEntity<StudentPredmetDto> update(@PathVariable Long id, @RequestBody StudentPredmetDto studentPredmetDto) {
        if(studentPredmetDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        StudentPredmet studentPredmet = studentPredmetService.save(studentPredmetMapper.toEntity(studentPredmetDto));
        return new ResponseEntity<StudentPredmetDto>(studentPredmetMapper.toDto(studentPredmet), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NASTAVNIK')")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<StudentPredmet> studentPredmet = studentPredmetService.getOne(id);
        if(!studentPredmet.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        studentPredmetService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
