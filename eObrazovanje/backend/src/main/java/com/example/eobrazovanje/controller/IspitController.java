package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.IspitDto;
import com.example.eobrazovanje.dto.PredmetDto;
import com.example.eobrazovanje.mapper.IspitMapper;
import com.example.eobrazovanje.model.Ispit;
import com.example.eobrazovanje.model.Predmet;
import com.example.eobrazovanje.model.Role;
import com.example.eobrazovanje.model.User;
import com.example.eobrazovanje.service.IspitService;
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
@RequestMapping("/api/ispiti")
public class IspitController {

    @Autowired
    IspitService ispitService;

    @Autowired
    IspitMapper ispitMapper;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<IspitDto>> getAll(Principal principal, Pageable pageable, @RequestParam(defaultValue = "") String search,
                                                 @RequestParam(required = false) String studentUsername) {
        String username = principal.getName();
        User user = userService.getOne(username);
        if (user.getRole() == Role.ADMIN) {
            username = studentUsername;
        } else if (user.getRole() == Role.STUDENT) {
            username = principal.getName();
        }
        Page<Ispit> ispiti = ispitService.getAll(pageable, search, username);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(ispiti.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(ispiti.getTotalPages()));
        return new ResponseEntity<List<IspitDto>>(ispitMapper.toDto(ispiti.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IspitDto> getOne(@PathVariable Long id) {
        Optional<Ispit> ispit = ispitService.getOne(id);
        if(!ispit.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<IspitDto>(ispitMapper.toDto(ispit.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<IspitDto> create(@RequestBody IspitDto ispitDto) throws Exception {
        if(ispitDto.getId() != null && ispitDto.getId() > 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Ispit ispit = ispitService.save(ispitMapper.toEntity(ispitDto));
        return new ResponseEntity<IspitDto>(ispitMapper.toDto(ispit), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<IspitDto> update(@PathVariable Long id, @RequestBody IspitDto ispitDto) {
        if(ispitDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            Ispit ispit = ispitService.save(ispitMapper.toEntity(ispitDto));
            return new ResponseEntity<IspitDto>(ispitMapper.toDto(ispit), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
