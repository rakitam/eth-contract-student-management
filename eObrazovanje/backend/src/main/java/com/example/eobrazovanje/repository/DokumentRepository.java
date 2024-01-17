package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Dokument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DokumentRepository extends JpaRepository<Dokument, Long> {

    Page<Dokument> findAllByStudent_ImeContainsOrStudent_PrezimeContainsOrNazivContains(String ime, String prezime, String naziv, Pageable pageable);

    Page<Dokument> findAllByStudentUsername(Pageable pageable, String username);

}
