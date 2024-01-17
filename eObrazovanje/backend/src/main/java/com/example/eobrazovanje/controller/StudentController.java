package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.StudentDto;
import com.example.eobrazovanje.mapper.StudentMapper;
import com.example.eobrazovanje.model.Student;
import com.example.eobrazovanje.model.User;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/studenti")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Autowired
    StudentMapper studentMapper;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll(Pageable pageable, @RequestParam(defaultValue = "") String search) {
        Page<Student> studenti = studentService.getAll(pageable, search);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(studenti.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(studenti.getTotalPages()));
        return new ResponseEntity<List<StudentDto>>(studentMapper.toDto(studenti.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<StudentDto> getOne(@PathVariable String username) {
        Optional<Student> student = studentService.getOne(username);
        if(!student.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<StudentDto>(studentMapper.toDto(student.get()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
        String username = studentDto.getUsername();
        String jmbg = studentDto.getJmbg();
        String brojIndeksa = studentDto.getBrojIndeksa();
        try {
            User userByUsername = userService.findByUsername(username);
            if(userByUsername != null || studentService.existsByJmbg(jmbg) || studentService.existsByBrIndeksa(brojIndeksa)) {
                return new ResponseEntity("Korisnik već postoji u bazi podataka.", HttpStatus.BAD_REQUEST);
            }
            Student student = studentService.save(studentMapper.toEntity(studentDto), true);
            return new ResponseEntity<StudentDto>(studentMapper.toDto(student), HttpStatus.CREATED);
        } catch (NumberFormatException ex) {
            return new ResponseEntity("Nisu uneti dobri podaci, molimo pokušajte ponovo.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        if(studentDto.getId() != id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Student student = studentService.save(studentMapper.toEntity(studentDto), false);
            return new ResponseEntity<StudentDto>(studentMapper.toDto(student), HttpStatus.OK);
        } catch (NumberFormatException ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Student> student = studentService.getOne(id);
        if(!student.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
