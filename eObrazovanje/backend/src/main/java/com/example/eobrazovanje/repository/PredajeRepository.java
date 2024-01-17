package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Predaje;
import com.example.eobrazovanje.model.Predmet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredajeRepository extends JpaRepository<Predaje, Long> {
    @Query(value = "select p from Predaje p " +
            "where (p.nastavnik.ime like %:search% or p.nastavnik.prezime like %:search% or p.predmet.naziv like %:search%) " +
            "and (COALESCE(:onlyIds,NULL) IS NULL or (COALESCE(:onlyIds, NULL) IS NOT NULL and p.id in :onlyIds))")
    Page<Predaje> findAllByFilter(String search, List<Long> onlyIds, Pageable pageable);

    List<Predaje> findAllByPredmetIn(List<Predmet> predmeti);

    void deleteAllByNastavnikId(Long nastavnikId);
}
