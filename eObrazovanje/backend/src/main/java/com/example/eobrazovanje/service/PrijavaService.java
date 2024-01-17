package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Ispit;
import com.example.eobrazovanje.model.Prijava;
import com.example.eobrazovanje.repository.PrijavaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrijavaService {

    @Autowired
    PrijavaRepository repo;

    public Page<Prijava> getAll(Pageable pageable, String search, String studentUsername) {
        if (studentUsername!=null && !studentUsername.isEmpty()) {
            return repo.findAllByStudentUsername(pageable, studentUsername);
        }
        return repo.findAllByStudent_ImeContainsOrStudent_PrezimeContainsOrIspit_Predaje_Nastavnik_ImeContainsOrIspit_Predaje_Nastavnik_PrezimeContainsOrIspit_Predaje_Predmet_NazivContains(search, search, search, search, search, pageable);
    }

    public Optional<Prijava> getOne(Long id) {
        return repo.findById(id);
    }

    public Prijava save(Prijava prijava) {
        Ispit ispit = new Ispit();
        ispit.setStudent(prijava.getStudent());
        ispit.setDatum(prijava.getIspitniRok().getDatumIspita());
        ispit.setKonacno(false);
        ispit.setPredaje(prijava.getIspitniRok().getPredaje());
        ispit.setBodovi(0);
        ispit.setPrijava(prijava);
        prijava.setIspit(ispit);
        return repo.save(prijava);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
