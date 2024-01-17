package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.NastavnikDto;
import com.example.eobrazovanje.mapper.NastavnikMapper;
import com.example.eobrazovanje.model.Nastavnik;
import com.example.eobrazovanje.model.User;
import com.example.eobrazovanje.service.NastavnikService;
import com.example.eobrazovanje.service.PredajeService;
import com.example.eobrazovanje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nastavnici")
public class NastavnikController {

    @Autowired
    NastavnikService nastavnikService;

    @Autowired
    NastavnikMapper nastavnikMapper;

    @Autowired
    UserService userService;

    @Autowired
    PredajeService predajeService;

    @GetMapping
    public ResponseEntity<List<NastavnikDto>> getAll(Pageable pageable, @RequestParam(defaultValue = "") String search) {
        Page<Nastavnik> nastavniki = nastavnikService.getAll(pageable, search);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(nastavniki.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(nastavniki.getTotalPages()));
        return new ResponseEntity<List<NastavnikDto>>(nastavnikMapper.toDto(nastavniki.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NastavnikDto> getOne(@PathVariable Long id) {
        Optional<Nastavnik> nastavnik = nastavnikService.getOne(id);
        if(!nastavnik.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<NastavnikDto>(nastavnikMapper.toDto(nastavnik.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<NastavnikDto> create(@RequestBody NastavnikDto nastavnikDto) {
        String username = nastavnikDto.getUsername();
        String jmbg = nastavnikDto.getJmbg();
        boolean exists = nastavnikService.existsByJmbg(jmbg);
        try {
            User userByUsername = userService.findByUsername(username);
            if(userByUsername != null || exists) {
                return new ResponseEntity("Korisnik već postoji u bazi podataka.", HttpStatus.BAD_REQUEST);
            }
            Nastavnik nastavnik = nastavnikService.save(nastavnikMapper.toEntity(nastavnikDto), true);
            return new ResponseEntity<NastavnikDto>(nastavnikMapper.toDto(nastavnik), HttpStatus.CREATED);
        } catch (NumberFormatException ex) {
            return new ResponseEntity("Nisu uneti dobri podaci, molimo pokušajte ponovo.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<NastavnikDto> update(@PathVariable Long id, @RequestBody NastavnikDto nastavnikDto) {
        if(nastavnikDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            User userByUsername = userService.findByUsername(nastavnikDto.getUsername());
            if(userByUsername != null) {
                return new ResponseEntity("Korisničko ime već postoji.", HttpStatus.BAD_REQUEST);
            }
            Nastavnik nastavnik = nastavnikService.save(nastavnikMapper.toEntity(nastavnikDto), false);
            return new ResponseEntity<NastavnikDto>(nastavnikMapper.toDto(nastavnik), HttpStatus.OK);
        } catch (NumberFormatException ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Nastavnik> nastavnik = nastavnikService.getOne(id);
        if(!nastavnik.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        predajeService.deleteAllByNastavnikId(id);
        nastavnikService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
