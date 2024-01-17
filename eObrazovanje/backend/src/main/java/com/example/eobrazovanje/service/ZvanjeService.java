package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Zvanje;
import com.example.eobrazovanje.repository.ZvanjeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZvanjeService {

    @Autowired
    ZvanjeRepository repo;

    public Page<Zvanje> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<Zvanje> getOne(Long id) {
        return repo.findById(id);
    }

    public Zvanje save(Zvanje zvanje) {
        return repo.save(zvanje);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
