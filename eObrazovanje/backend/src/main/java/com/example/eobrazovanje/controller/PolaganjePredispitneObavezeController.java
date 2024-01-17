package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.PolaganjePredispitneObavezeDto;
import com.example.eobrazovanje.mapper.PolaganjePredispitneObavezeMapper;
import com.example.eobrazovanje.model.PolaganjePredispitneObaveze;
import com.example.eobrazovanje.model.Role;
import com.example.eobrazovanje.model.User;
import com.example.eobrazovanje.service.PolaganjePredispitneObavezeService;
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
@RequestMapping("/api/polaganje-predispitne-obaveze")
public class PolaganjePredispitneObavezeController {

    @Autowired
    PolaganjePredispitneObavezeService polaganjePredispitneObavezeService;

    @Autowired
    PolaganjePredispitneObavezeMapper polaganjePredispitneObavezeMapper;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<PolaganjePredispitneObavezeDto>> getAll(Principal principal, Pageable pageable,
                                                                       @RequestParam(required = false) String studentUsername) {
        String username = principal.getName();
        User user = userService.getOne(username);
        if (user.getRole() == Role.ADMIN) {
            username = studentUsername;
        } else if (user.getRole() == Role.STUDENT) {
            username = principal.getName();
        }
        Page<PolaganjePredispitneObaveze> ispiti = polaganjePredispitneObavezeService.getAll(pageable, username);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(ispiti.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(ispiti.getTotalPages()));
        return new ResponseEntity<List<PolaganjePredispitneObavezeDto>>(polaganjePredispitneObavezeMapper.toDto(ispiti.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolaganjePredispitneObavezeDto> getOne(@PathVariable Long id) {
        Optional<PolaganjePredispitneObaveze> polaganjePredispitneObaveze = polaganjePredispitneObavezeService.getOne(id);
        if(!polaganjePredispitneObaveze.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PolaganjePredispitneObavezeDto>(polaganjePredispitneObavezeMapper.toDto(polaganjePredispitneObaveze.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'NASTAVNIK')")
    public ResponseEntity<PolaganjePredispitneObavezeDto> create(@RequestBody PolaganjePredispitneObavezeDto polaganjePredispitneObavezeDto) {
        PolaganjePredispitneObaveze polaganjePredispitneObaveze = polaganjePredispitneObavezeService.save(polaganjePredispitneObavezeMapper.toEntity(polaganjePredispitneObavezeDto));
        return new ResponseEntity<PolaganjePredispitneObavezeDto>(polaganjePredispitneObavezeMapper.toDto(polaganjePredispitneObaveze), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NASTAVNIK')")
    public ResponseEntity<PolaganjePredispitneObavezeDto> update(@PathVariable Long id, @RequestBody PolaganjePredispitneObavezeDto polaganjePredispitneObavezeDto) {
        if(polaganjePredispitneObavezeDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        PolaganjePredispitneObaveze polaganjePredispitneObaveze = polaganjePredispitneObavezeService.save(polaganjePredispitneObavezeMapper.toEntity(polaganjePredispitneObavezeDto));
        return new ResponseEntity<PolaganjePredispitneObavezeDto>(polaganjePredispitneObavezeMapper.toDto(polaganjePredispitneObaveze), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NASTAVNIK')")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<PolaganjePredispitneObaveze> polaganjePredispitneObaveze = polaganjePredispitneObavezeService.getOne(id);
        if(!polaganjePredispitneObaveze.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        polaganjePredispitneObavezeService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
