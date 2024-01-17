package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.DokumentDto;
import com.example.eobrazovanje.mapper.DokumentMapper;
import com.example.eobrazovanje.mapper.StudentMapper;
import com.example.eobrazovanje.model.Dokument;
import com.example.eobrazovanje.model.Role;
import com.example.eobrazovanje.model.Student;
import com.example.eobrazovanje.model.User;
import com.example.eobrazovanje.service.DokumentService;
import com.example.eobrazovanje.service.StudentService;
import com.example.eobrazovanje.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dokumenti")
public class DokumentController {

    @Autowired
    DokumentService dokumentService;

    @Autowired
    UserService userService;

    @Autowired
    DokumentMapper dokumentMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentService studentService;

    @Value("${upload.path}")
    String uploadPath;

    @GetMapping
    public ResponseEntity<List<DokumentDto>> getAll(Principal principal, Pageable pageable,
                                                    @RequestParam(defaultValue = "") String search,
                                                    @RequestParam(required = false) String studentUsername) {
        String username = principal.getName();
        User user = userService.getOne(username);
        if (user.getRole() == Role.ADMIN) {
            username = studentUsername;
        } else if (user.getRole() == Role.STUDENT) {
            username = principal.getName();
        }
        Page<Dokument> dokumenti = dokumentService.getAll(pageable, search, username);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TOTAL-COUNT", String.valueOf(dokumenti.getTotalElements()));
        headers.set("X-TOTAL-PAGES", String.valueOf(dokumenti.getTotalPages()));
        return new ResponseEntity<List<DokumentDto>>(dokumentMapper.toDto(dokumenti.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DokumentDto> getOne(@PathVariable Long id) {
        Optional<Dokument> dokument = dokumentService.getOne(id);
        if(!dokument.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DokumentDto>(dokumentMapper.toDto(dokument.get()), HttpStatus.OK);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> getContent(@PathVariable Long id) throws IOException {
        Optional<Dokument> dokument = dokumentService.getOne(id);
        if(!dokument.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        File file = new File(uploadPath+dokument.get().getUrl());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(Files.probeContentType(file.toPath())))
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<DokumentDto> create(Principal principal, @RequestPart("file") MultipartFile file, @RequestPart("dokument") DokumentDto dokumentDto) throws IOException {
        String username = principal.getName();
        User user = userService.getOne(username);
        if (user.getRole() == Role.STUDENT) {
            dokumentDto.setStudent(studentMapper.toDto((Student) user));
        }
        dokumentDto.setUrl("");
        Dokument dokument = dokumentService.save(dokumentMapper.toEntity(dokumentDto));
        String fileName = dokument.getId()+ "." + FilenameUtils.getExtension(file.getOriginalFilename());
        File filePath = new File(uploadPath+ fileName);
        file.transferTo(filePath);
        dokument.setUrl(fileName);
        dokument = dokumentService.save(dokument);
        return new ResponseEntity<DokumentDto>(dokumentMapper.toDto(dokument), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DokumentDto> update(Principal principal, @PathVariable Long id, @RequestPart(value = "file", required = false) MultipartFile file, @RequestPart("dokument") DokumentDto dokumentDto) throws IOException {
        if(dokumentDto.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        String username = principal.getName();
        User user = userService.getOne(username);
        if (user.getRole() == Role.STUDENT) {
            dokumentDto.setStudent(studentMapper.toDto((Student) user));
        }
        Dokument dokument = dokumentService.getOne(dokumentDto.getId()).get();
        String oldUrl = "";
        String newUrl = "";
        if (file!=null) {
            oldUrl = dokument.getUrl();
            newUrl = dokument.getId()+ "." + FilenameUtils.getExtension(file.getOriginalFilename());
            File filePath = new File(uploadPath+ newUrl);
            file.transferTo(filePath);
            dokumentDto.setUrl(newUrl);
        }
        dokument = dokumentService.save(dokumentMapper.toEntity(dokumentDto));
        if (!oldUrl.equals(newUrl)) {
            dokumentService.deleteFromFileSystem(oldUrl);
        }
        return new ResponseEntity<DokumentDto>(dokumentMapper.toDto(dokument), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Principal principal, @PathVariable Long id) {
        String username = principal.getName();
        User user = userService.getOne(username);
        Optional<Dokument> dokument = dokumentService.getOne(id);
        if(!dokument.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!dokument.get().getStudent().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        dokumentService.delete(dokument.get());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
