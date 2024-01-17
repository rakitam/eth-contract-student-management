package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Prijava;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrijavaRepository extends JpaRepository<Prijava, Long> {

    Page<Prijava> findAllByStudent_ImeContainsOrStudent_PrezimeContainsOrIspit_Predaje_Nastavnik_ImeContainsOrIspit_Predaje_Nastavnik_PrezimeContainsOrIspit_Predaje_Predmet_NazivContains(String studentIme, String studentPrezime, String nastavnikIme, String nastavnikPrezime, String predmetNaziv, Pageable pageable);

    Page<Prijava> findAllByStudentUsername(Pageable pageable, String username);

}
