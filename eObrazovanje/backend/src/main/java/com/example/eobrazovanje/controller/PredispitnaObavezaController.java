package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.PredispitnaObavezaDto;
import com.example.eobrazovanje.mapper.PredispitnaObavezaMapper;
import com.example.eobrazovanje.model.PredispitnaObaveza;
import com.example.eobrazovanje.service.PredispitnaObavezaService;
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
@RequestMapping("/api/predispitne-obaveze")
public class PredispitnaObavezaController {

    @Autowired
    PredispitnaObavezaService predispitnaObavezaService;

    @Autowired
    PredispitnaObavezaMapper predispitnaObavezaMapper;

    @GetMapping
    public ResponseEntity<List<PredispitnaObavezaDto>> getAll(Pageable pageable, @RequestParam(required = false) Long predmetId) {
        if (predmetId == null) {
            Page<PredispitnaObaveza> predispitneObaveze = predispitnaObavezaService.getAll(pageable);
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-TOTAL-COUNT", String.valueOf(predispitneObaveze.getTotalElements()));
            headers.set("X-TOTAL-PAGES", String.valueOf(predispitneObaveze.getTotalPages()));
            return new ResponseEntity<List<PredispitnaObavezaDto>>(predispitnaObavezaMapper.toDto(predispitneObaveze.toList()), headers, HttpStatus.OK);
        }
        List<PredispitnaObaveza> predispitneObaveze = predispitnaObavezaService.getAllByPredmet(predmetId);
        return new ResponseEntity<List<PredispitnaObavezaDto>>(predispitnaObavezaMapper.toDto(predispitneObaveze), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PredispitnaObavezaDto> getOne(@PathVariable Long id) {
        Optional<PredispitnaObaveza> predispitnaObaveza = predispitnaObavezaService.getOne(id);
        if(!predispitnaObaveza.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PredispitnaObavezaDto>(predispitnaObavezaMapper.toDto(predispitnaObaveza.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'NASTAVNIK')")
    public ResponseEntity<PredispitnaObavezaDto> create(@RequestBody PredispitnaObavezaDto predispitnaObavezaDto) {
        PredispitnaObaveza predispitnaObaveza = predispitnaObavezaService.save(predispitnaObavezaMapper.toEntity(predispitnaObavezaDto));
        return new ResponseEntity<PredispitnaObavezaDto>(predispitnaObavezaMapper.toDto(predispitnaObaveza), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NASTAVNIK')")
    public ResponseEntity<PredispitnaObavezaDto> update(@PathVariable Long id, @RequestBody PredispitnaObavezaDto predispitnaObavezaDto) {
        if(predispitnaObavezaDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        PredispitnaObaveza predispitnaObaveza = predispitnaObavezaService.save(predispitnaObavezaMapper.toEntity(predispitnaObavezaDto));
        return new ResponseEntity<PredispitnaObavezaDto>(predispitnaObavezaMapper.toDto(predispitnaObaveza), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NASTAVNIK')")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<PredispitnaObaveza> predispitnaObaveza = predispitnaObavezaService.getOne(id);
        if(!predispitnaObaveza.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        predispitnaObavezaService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
