package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.IspitniRok;
import com.example.eobrazovanje.model.Predmet;
import com.example.eobrazovanje.repository.IspitniRokRepository;
import com.example.eobrazovanje.repository.StudentPredmetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IspitniRokService {

    @Autowired
    IspitniRokRepository repo;

    @Autowired
    StudentPredmetRepository studentPredmetRepository;

    public Page<IspitniRok> getAll(Pageable pageable, String search) {
        return repo.findAllByPredajePredmetNazivContainsOrPredajeNastavnikImeContainsOrPredajeNastavnikPrezimeContains(pageable, search, search, search);
    }

    public Page<IspitniRok> getAllByStudentUsername(Pageable pageable, String student) {

        List<Predmet> predmeti = studentPredmetRepository.findAllByStudentUsername(student)
                .stream()
                .map(st -> st.getPredmet()).collect(Collectors.toList());

        return repo.findAllByPredajePredmetIn(predmeti, pageable);
    }

    public Optional<IspitniRok> getOne(Long id) {
        return repo.findById(id);
    }

    public IspitniRok save(IspitniRok ispitniRok) {
        return repo.save(ispitniRok);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Page<IspitniRok> getAktivniIspitniRokovi(Pageable pageable) {
        return repo.findByRok_PocetakBeforeAndRok_KrajAfter(LocalDate.now(), LocalDate.now(), pageable);
    }
}
