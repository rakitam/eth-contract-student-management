package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Nastavnik;
import com.example.eobrazovanje.model.Role;
import com.example.eobrazovanje.repository.NastavnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NastavnikService {

    @Autowired
    NastavnikRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Nastavnik> getAll(Pageable pageable, String search) {
        return repo.findAllByUsernameContainsOrImeContainsOrPrezimeContainsOrJmbgContainsOrAdresaContains(search, search, search, search, search, pageable);
    }

    public Optional<Nastavnik> getOne(Long id) {
        return repo.findById(id);
    }

    public Nastavnik save(Nastavnik nastavnik, boolean kreiraSe) throws NumberFormatException{
        Long.parseLong(nastavnik.getJmbg());
        nastavnik.setRole(Role.NASTAVNIK);
        if (kreiraSe) {
            nastavnik.setPassword(passwordEncoder.encode("admin"));
        }
        return repo.save(nastavnik);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public boolean existsByJmbg(String jmbg) {
        return repo.getByJmbg(jmbg).isPresent();
    }
}
