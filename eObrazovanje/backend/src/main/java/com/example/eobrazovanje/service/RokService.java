package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Rok;
import com.example.eobrazovanje.repository.RokRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RokService {

    @Autowired
    RokRepository repo;

    public Page<Rok> getAll(Pageable pageable, String search) {
        return repo.findAllByNazivContains(search, pageable);
    }

    public Optional<Rok> getOne(Long id) {
        return repo.findById(id);
    }

    public Rok save(Rok rok) {
        Optional<Rok> rokExists = exists(rok);
        if (rokExists.isPresent() && !rokExists.get().getId().equals(rok.getId())) {
            return null;
        }
        return repo.save(rok);
    }

    public Optional<Rok> exists(Rok rok) {
        return repo.findFirstByPocetakBetweenOrKrajBetween(rok.getPocetak(),rok.getKraj(),rok.getPocetak(),rok.getKraj());
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Rok getAktivniRok() {
        return repo.findRokByPocetakBeforeAndKrajAfter(LocalDate.now(),LocalDate.now()).orElse(null);
    }
}
