package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Predmet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredmetRepository extends JpaRepository<Predmet, Long> {
    Page<Predmet> findAllByNazivContainsOrOznakaContains(String naziv, String oznaka, Pageable pageable);
}
