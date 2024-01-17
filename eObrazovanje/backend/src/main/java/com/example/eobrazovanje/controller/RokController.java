package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.RokDto;
import com.example.eobrazovanje.mapper.RokMapper;
import com.example.eobrazovanje.model.Rok;
import com.example.eobrazovanje.service.RokService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rokovi")
public class RokController {

    @Autowired
    RokService rokService;

    @Autowired
    RokMapper rokMapper;

    @GetMapping
    public ResponseEntity<List<RokDto>> getAll(Pageable pageable, String search) {
        Page<Rok> roki = rokService.getAll(pageable, search);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(roki.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(roki.getTotalPages()));
        return new ResponseEntity<List<RokDto>>(rokMapper.toDto(roki.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/aktivni/rok")
    public ResponseEntity<RokDto> getAktivniRok() {
        return new ResponseEntity<RokDto>(rokMapper.toDto(rokService.getAktivniRok()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RokDto> getOne(@PathVariable Long id) {
        Optional<Rok> rok = rokService.getOne(id);
        if(!rok.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<RokDto>(rokMapper.toDto(rok.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RokDto> create(@RequestBody RokDto rokDto) {
        Rok rok = rokService.save(rokMapper.toEntity(rokDto));
        if(rok == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RokDto>(rokMapper.toDto(rok), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RokDto> update(@PathVariable Long id, @RequestBody RokDto rokDto) {
        if(rokDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Rok rok = rokService.save(rokMapper.toEntity(rokDto));
        if(rok == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RokDto>(rokMapper.toDto(rok), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Rok> rok = rokService.getOne(id);
        if(!rok.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        rokService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
