package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Ispit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IspitRepository extends JpaRepository<Ispit, Long> {
    Page<Ispit> findAllByPredajePredmetNazivContainsOrPredajeNastavnikImeContainsOrPredajeNastavnikPrezimeContainsOrStudentImeContainsOrStudentPrezimeContains(Pageable pageable, String naziv, String nastavnikIme, String nastavnikPrezime, String studentIme, String studentPrezime);
    Page<Ispit> findAllByStudentUsername(Pageable pageable, String username);
}
