package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.PredajeDto;
import com.example.eobrazovanje.mapper.PredajeMapper;
import com.example.eobrazovanje.model.Predaje;
import com.example.eobrazovanje.service.PredajeService;
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
@RequestMapping("/api/predaje")
public class PredajeController {

    @Autowired
    PredajeService predajeService;

    @Autowired
    PredajeMapper predajeMapper;

    @GetMapping
    public ResponseEntity<List<PredajeDto>> getAll(Pageable pageable, @RequestParam(defaultValue = "") String search,
                                                   @RequestParam(defaultValue = "false") Boolean aktivniZaRok ) {
        Page<Predaje> predajei = predajeService.getAll(pageable, search, aktivniZaRok);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(predajei.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(predajei.getTotalPages()));
        return new ResponseEntity<List<PredajeDto>>(predajeMapper.toDto(predajei.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PredajeDto> getOne(@PathVariable Long id) {
        Optional<Predaje> predaje = predajeService.getOne(id);
        if(!predaje.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PredajeDto>(predajeMapper.toDto(predaje.get()), HttpStatus.OK);
    }

    @GetMapping("/predavaci/{username}")
    public ResponseEntity<List<Predaje>> getPredavaciForStudent(@PathVariable String username) {
        List<Predaje> predavaci = predajeService.getPredavaciForStudent(username);
        return new ResponseEntity<>(predavaci, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PredajeDto> create(@RequestBody PredajeDto predajeDto) {
        Predaje predaje = predajeService.save(predajeMapper.toEntity(predajeDto));
        return new ResponseEntity<PredajeDto>(predajeMapper.toDto(predaje), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PredajeDto> update(@PathVariable Long id, @RequestBody PredajeDto predajeDto) {
        if(predajeDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Predaje predaje = predajeService.save(predajeMapper.toEntity(predajeDto));
        return new ResponseEntity<PredajeDto>(predajeMapper.toDto(predaje), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Predaje> predaje = predajeService.getOne(id);
        if(!predaje.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        predajeService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
