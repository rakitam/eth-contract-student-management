package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Dokument;
import com.example.eobrazovanje.model.Uplata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UplataRepository extends JpaRepository<Uplata, Long> {
    Page<Uplata> findAllByStudent_ImeContainsOrStudent_PrezimeContains(String ime, String prezime, Pageable pageable);

    Page<Uplata> findAllByStudentUsername(Pageable pageable, String username);
}
