package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.IspitniRokDto;
import com.example.eobrazovanje.mapper.IspitniRokMapper;
import com.example.eobrazovanje.model.IspitniRok;
import com.example.eobrazovanje.model.Role;
import com.example.eobrazovanje.model.User;
import com.example.eobrazovanje.service.IspitniRokService;
import com.example.eobrazovanje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ispitni-rokovi")
public class IspitniRokController {

    @Autowired
    IspitniRokService ispitniRokService;

    @Autowired
    IspitniRokMapper ispitniRokMapper;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<IspitniRokDto>> getAll(Principal principal, Pageable pageable, @RequestParam(defaultValue = "") String student,
                                                      @RequestParam(defaultValue = "") String search) {
        String username = principal.getName();
        User user = userService.getOne(username);
        if (user.getRole() == Role.ADMIN) {
            username = student;
        } else if (user.getRole() == Role.STUDENT) {
            username = user.getUsername();
        }

        Page<IspitniRok> ispitniRoki;
        if (user.getRole() == Role.ADMIN && username.isEmpty()) {
            ispitniRoki = ispitniRokService.getAll(pageable, search);
        } else {
            ispitniRoki = ispitniRokService.getAllByStudentUsername(pageable, username);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(ispitniRoki.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(ispitniRoki.getTotalPages()));
        return new ResponseEntity<List<IspitniRokDto>>(ispitniRokMapper.toDto(ispitniRoki.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IspitniRokDto> getOne(@PathVariable Long id) {
        Optional<IspitniRok> ispitniRok = ispitniRokService.getOne(id);
        if(!ispitniRok.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<IspitniRokDto>(ispitniRokMapper.toDto(ispitniRok.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<IspitniRokDto> create(@RequestBody IspitniRokDto ispitniRokDto) {
        IspitniRok ispitniRok = ispitniRokService.save(ispitniRokMapper.toEntity(ispitniRokDto));
        return new ResponseEntity<IspitniRokDto>(ispitniRokMapper.toDto(ispitniRok), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<IspitniRokDto> update(@PathVariable Long id, @RequestBody IspitniRokDto ispitniRokDto) {
        if(ispitniRokDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        IspitniRok ispitniRok = ispitniRokService.save(ispitniRokMapper.toEntity(ispitniRokDto));
        return new ResponseEntity<IspitniRokDto>(ispitniRokMapper.toDto(ispitniRok), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<IspitniRok> ispitniRok = ispitniRokService.getOne(id);
        if(!ispitniRok.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        ispitniRokService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
