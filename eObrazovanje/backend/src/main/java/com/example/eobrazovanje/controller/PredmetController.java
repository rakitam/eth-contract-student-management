package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.PredmetDto;
import com.example.eobrazovanje.mapper.PredmetMapper;
import com.example.eobrazovanje.model.Predmet;
import com.example.eobrazovanje.service.PredmetService;
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
@RequestMapping("/api/predmeti")
public class PredmetController {

    @Autowired
    PredmetService predmetService;

    @Autowired
    PredmetMapper predmetMapper;

    @GetMapping
    public ResponseEntity<List<PredmetDto>> getAll(Pageable pageable, @RequestParam(defaultValue = "") String search) {
        Page<Predmet> predmeti = predmetService.getAll(pageable, search);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(predmeti.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(predmeti.getTotalPages()));
        return new ResponseEntity<List<PredmetDto>>(predmetMapper.toDto(predmeti.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PredmetDto> getOne(@PathVariable Long id) {
        Optional<Predmet> predmet = predmetService.getOne(id);
        if(!predmet.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PredmetDto>(predmetMapper.toDto(predmet.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PredmetDto> create(@RequestBody PredmetDto predmetDto) {
        if(predmetDto.getId() != null && predmetDto.getId() > 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Predmet predmet = predmetService.save(predmetMapper.toEntity(predmetDto));
        return new ResponseEntity<PredmetDto>(predmetMapper.toDto(predmet), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PredmetDto> update(@PathVariable Long id, @RequestBody PredmetDto predmetDto) {
        if(predmetDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Predmet predmet = predmetService.save(predmetMapper.toEntity(predmetDto));
        return new ResponseEntity<PredmetDto>(predmetMapper.toDto(predmet), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Predmet> predmet = predmetService.getOne(id);
        if(!predmet.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        predmetService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
