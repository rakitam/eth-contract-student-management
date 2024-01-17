package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Nastavnik;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NastavnikRepository extends JpaRepository<Nastavnik, Long> {
    Optional<Nastavnik> getByJmbg(String jmbg);
    Page<Nastavnik> findAllByUsernameContainsOrImeContainsOrPrezimeContainsOrJmbgContainsOrAdresaContains(String username, String ime, String prezime, String jmbg, String adresa, Pageable pageable);

}
