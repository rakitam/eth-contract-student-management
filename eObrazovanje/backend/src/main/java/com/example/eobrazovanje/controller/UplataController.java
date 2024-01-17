package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.UplataDto;
import com.example.eobrazovanje.mapper.UplataMapper;
import com.example.eobrazovanje.model.Role;
import com.example.eobrazovanje.model.Uplata;
import com.example.eobrazovanje.model.User;
import com.example.eobrazovanje.service.UplataService;
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
@RequestMapping("/api/uplate")
public class UplataController {

    @Autowired
    UplataService uplataService;

    @Autowired
    UserService userService;

    @Autowired
    UplataMapper uplataMapper;

    @GetMapping
    public ResponseEntity<List<UplataDto>> getAll(Principal principal, Pageable pageable, @RequestParam(defaultValue = "") String search,
                                                  @RequestParam(required = false) String studentUsername) {
        String username = principal.getName();
        User user = userService.getOne(username);
        if (user.getRole() == Role.ADMIN) {
            username = studentUsername;
        } else if (user.getRole() == Role.STUDENT) {
            username = principal.getName();
        }
        Page<Uplata> uplatai = uplataService.getAll(pageable, search, username);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(uplatai.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(uplatai.getTotalPages()));
        return new ResponseEntity<List<UplataDto>>(uplataMapper.toDto(uplatai.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UplataDto> getOne(@PathVariable Long id) {
        Optional<Uplata> uplata = uplataService.getOne(id);
        if(!uplata.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UplataDto>(uplataMapper.toDto(uplata.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<UplataDto> create(@RequestBody UplataDto uplataDto) {
        Uplata uplata = null;
        try {
            uplataDto.setStornirano(false);
            uplata = uplataService.save(uplataMapper.toEntity(uplataDto));
            return new ResponseEntity<UplataDto>(uplataMapper.toDto(uplata), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{id}/storniraj")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UplataDto> storniraj(@PathVariable Long id) {
        try {
            Uplata uplata = uplataService.storniraj(id);
            return new ResponseEntity<UplataDto>(uplataMapper.toDto(uplata), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
