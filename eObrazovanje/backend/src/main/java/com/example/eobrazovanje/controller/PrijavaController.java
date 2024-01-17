package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.PrijavaDto;
import com.example.eobrazovanje.mapper.PrijavaMapper;
import com.example.eobrazovanje.mapper.StudentMapper;
import com.example.eobrazovanje.model.Prijava;
import com.example.eobrazovanje.model.Role;
import com.example.eobrazovanje.model.Student;
import com.example.eobrazovanje.model.User;
import com.example.eobrazovanje.service.PrijavaService;
import com.example.eobrazovanje.service.StudentService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prijave")
public class PrijavaController {

    @Autowired
    PrijavaService prijavaService;

    @Autowired
    PrijavaMapper prijavaMapper;

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;


    @GetMapping
    public ResponseEntity<List<PrijavaDto>> getAll(Principal principal, Pageable pageable, @RequestParam(defaultValue = "") String search,
                                                   @RequestParam(required = false) String studentUsername) {
        String username = principal.getName();
        User user = userService.getOne(username);
        if (user.getRole() == Role.ADMIN) {
            username = studentUsername;
        } else if (user.getRole() == Role.STUDENT) {
            username = user.getUsername();
        }
        Page<Prijava> prijavai = prijavaService.getAll(pageable, search, username);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(prijavai.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(prijavai.getTotalPages()));
        return new ResponseEntity<List<PrijavaDto>>(prijavaMapper.toDto(prijavai.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrijavaDto> getOne(@PathVariable Long id) {
        Optional<Prijava> prijava = prijavaService.getOne(id);
        if(!prijava.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PrijavaDto>(prijavaMapper.toDto(prijava.get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PrijavaDto> create(Principal principal, @RequestBody PrijavaDto prijavaDto) {
        String username = principal.getName();
        User user = userService.getOne(username);
        if (user.getRole() == Role.STUDENT) {
            prijavaDto.setStudent(studentMapper.toDto((Student) user));
        }
        Prijava prijava = prijavaMapper.toEntity(prijavaDto);
        if (prijava.getIspitniRok().getRok().getPocetak().isAfter(LocalDate.now()) || prijava.getIspitniRok().getRok().getKraj().isBefore(LocalDate.now())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        prijava = prijavaService.save(prijava);
        Student student = prijava.getStudent();
        student.setStanje(student.getStanje() - 200);
        studentService.save(student, false);
        return new ResponseEntity<PrijavaDto>(prijavaMapper.toDto(prijava), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PrijavaDto> update(@PathVariable Long id, @RequestBody PrijavaDto prijavaDto) {
        if(prijavaDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Prijava prijava = prijavaService.save(prijavaMapper.toEntity(prijavaDto));
        return new ResponseEntity<PrijavaDto>(prijavaMapper.toDto(prijava), HttpStatus.OK);
    }

}
