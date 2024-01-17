package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.StudentPredmet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPredmetRepository extends JpaRepository<StudentPredmet, Long> {


    @Query(value = "select s from StudentPredmet s " +
            "where (s.predmet.naziv like %:search% or s.student.ime like %:search% or s.student.prezime like %:search%) " +
            "and s.osvojeniBodovi between :minBodovi and :maxBodovi and (:predmetId = 0L or s.predmet.id = :predmetId)")
    Page<StudentPredmet> findAllByFilter(String search, Integer minBodovi, Integer maxBodovi, Long predmetId, Pageable pageable);

    List<StudentPredmet> findAllByStudentUsername(String username);

}
