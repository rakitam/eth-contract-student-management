package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);
    Optional<Student> getByJmbg(String jmbg);
    Optional<Student> getByBrojIndeksa(String brIndeksa);

    Page<Student> findAllByUsernameContainsOrImeContainsOrPrezimeContainsOrJmbgContainsOrAdresaContains(String username, String ime, String prezime, String jmbg, String adresa, Pageable pageable);
}
