package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Predmet;
import com.example.eobrazovanje.repository.PredmetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PredmetService {

    @Autowired
    PredmetRepository repo;

    public Page<Predmet> getAll(Pageable pageable, String search) {
        return repo.findAllByNazivContainsOrOznakaContains(search, search, pageable);
    }

    public Optional<Predmet> getOne(Long id) {
        return repo.findById(id);
    }

    public Predmet save(Predmet predmet) {
        return repo.save(predmet);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
