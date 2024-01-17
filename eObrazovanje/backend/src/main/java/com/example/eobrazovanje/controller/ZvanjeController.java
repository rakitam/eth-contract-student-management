package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.ZvanjeDto;
import com.example.eobrazovanje.mapper.ZvanjeMapper;
import com.example.eobrazovanje.model.Zvanje;
import com.example.eobrazovanje.service.ZvanjeService;
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
@RequestMapping("/api/zvanja")
public class ZvanjeController {

    @Autowired
    ZvanjeService zvanjeService;

    @Autowired
    ZvanjeMapper zvanjeMapper;

    @GetMapping
    public ResponseEntity<List<ZvanjeDto>> getAll(Pageable pageable) {
        Page<Zvanje> zvanje = zvanjeService.getAll(pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(zvanje.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(zvanje.getTotalPages()));
        return new ResponseEntity<List<ZvanjeDto>>(zvanjeMapper.toDto(zvanje.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZvanjeDto> getOne(@PathVariable Long id) {
        Optional<Zvanje> zvanje = zvanjeService.getOne(id);
        if(!zvanje.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ZvanjeDto>(zvanjeMapper.toDto(zvanje.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ZvanjeDto> create(@RequestBody ZvanjeDto zvanjeDto) {
        Zvanje zvanje = zvanjeService.save(zvanjeMapper.toEntity(zvanjeDto));
        return new ResponseEntity<ZvanjeDto>(zvanjeMapper.toDto(zvanje), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ZvanjeDto> update(@PathVariable Long id, @RequestBody ZvanjeDto zvanjeDto) {
        if(zvanjeDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Zvanje zvanje = zvanjeService.save(zvanjeMapper.toEntity(zvanjeDto));
        return new ResponseEntity<ZvanjeDto>(zvanjeMapper.toDto(zvanje), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Zvanje> zvanje = zvanjeService.getOne(id);
        if(!zvanje.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        zvanjeService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
