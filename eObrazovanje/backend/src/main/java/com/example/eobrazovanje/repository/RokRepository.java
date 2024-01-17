package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Dokument;
import com.example.eobrazovanje.model.Predmet;
import com.example.eobrazovanje.model.Rok;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RokRepository extends JpaRepository<Rok, Long> {
    Page<Rok> findAllByNazivContains(String naziv, Pageable pageable);

    Optional<Rok> findRokByPocetakBeforeAndKrajAfter(LocalDate pocetak, LocalDate kraj);

    Optional<Rok> findFirstByPocetakBetweenOrKrajBetween(LocalDate pocetakStart, LocalDate pocetakEnd, LocalDate krajStart, LocalDate krajEnd);

}
