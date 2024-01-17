package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Dokument;
import com.example.eobrazovanje.repository.DokumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class DokumentService {

    @Autowired
    DokumentRepository repo;

    @Value("${upload.path}")
    String uploadPath;

    public Page<Dokument> getAll(Pageable pageable, String search, String studentUsername) {
        if (studentUsername!=null && !studentUsername.isEmpty()) {
            return repo.findAllByStudentUsername(pageable, studentUsername);
        }
        return repo.findAllByStudent_ImeContainsOrStudent_PrezimeContainsOrNazivContains(search, search, search, pageable);
    }

    public Optional<Dokument> getOne(Long id) {
        return repo.findById(id);
    }

    public Dokument save(Dokument dokument) {
        return repo.save(dokument);
    }

    public void delete(Dokument dokument) {
        deleteFromFileSystem(dokument.getUrl());
        repo.deleteById(dokument.getId());
    }

    public boolean deleteFromFileSystem(String url) {
        File file = new File(uploadPath+url);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
