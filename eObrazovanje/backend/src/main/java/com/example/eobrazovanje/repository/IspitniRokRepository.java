package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Ispit;
import com.example.eobrazovanje.model.IspitniRok;
import com.example.eobrazovanje.model.Predmet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IspitniRokRepository extends JpaRepository<IspitniRok, Long> {

    Page<IspitniRok> findAllByPredajePredmetNazivContainsOrPredajeNastavnikImeContainsOrPredajeNastavnikPrezimeContains(Pageable pageable, String naziv, String nastavnikIme, String nastavnikPrezime);

    Page<IspitniRok> findAllByPredajePredmetIn(List<Predmet> predmeti, Pageable pageable);

    Page<IspitniRok> findByRok_PocetakBeforeAndRok_KrajAfter(LocalDate pocetak, LocalDate kraj, Pageable pageabl);
}
