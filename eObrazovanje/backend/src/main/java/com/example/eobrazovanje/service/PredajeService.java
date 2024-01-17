package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.*;
import com.example.eobrazovanje.repository.PredajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PredajeService {

    @Autowired
    PredajeRepository repo;

    @Autowired
    StudentPredmetService studentPredmetService;

    @Autowired
    IspitniRokService ispitniRokService;

    public Page<Predaje> getAll(Pageable pageable, String search, Boolean aktivniZaRok) {
        List<Long> ispitniRoks = new ArrayList<>();
        if (aktivniZaRok) {
            ispitniRoks = ispitniRokService.getAktivniIspitniRokovi(PageRequest.of(0, Integer.MAX_VALUE)).getContent().stream().map(ir -> ir.getPredaje().getId()).collect(Collectors.toList());
        }
        return repo.findAllByFilter(search, ispitniRoks, pageable);
    }

    public List<Predaje> getPredavaciForStudent(String username) {
        List<StudentPredmet> studentPredmeti = studentPredmetService.findAllByStudentUsername(username);

        List<Predmet> predmeti = studentPredmeti.stream()
                .map(StudentPredmet::getPredmet)
                .collect(Collectors.toList());

        List<Predaje> predavaci = new ArrayList<>();

        for (Predaje predaje : repo.findAllByPredmetIn(predmeti)) {
            if (isValidZvanje(predaje.getZvanje())) {
                predavaci.add(predaje);
            }
        }
        return predavaci;

    }

    private boolean isValidZvanje(Zvanje zvanje) {
        return zvanje != null &&
                (zvanje.getNaziv().equalsIgnoreCase("Profesor") || zvanje.getNaziv().equalsIgnoreCase("Asistent"));
    }

    public Optional<Predaje> getOne(Long id) {
        return repo.findById(id);
    }

    public Predaje save(Predaje predaje) {
        return repo.save(predaje);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public void deleteAllByNastavnikId(Long nastavnikId) {
        repo.deleteAllByNastavnikId(nastavnikId);
    }

}
