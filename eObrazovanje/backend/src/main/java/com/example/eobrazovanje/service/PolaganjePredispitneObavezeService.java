package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.PolaganjePredispitneObaveze;
import com.example.eobrazovanje.repository.PolaganjePredispitneObavezeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolaganjePredispitneObavezeService {

    @Autowired
    PolaganjePredispitneObavezeRepository repo;


    public Page<PolaganjePredispitneObaveze> getAll(Pageable pageable, String studentUsername) {
        if (studentUsername!=null && !studentUsername.isEmpty()) {
            return repo.findAllByStudentUsername(pageable, studentUsername);
        }
        return repo.findAll(pageable);
    }
    public Optional<PolaganjePredispitneObaveze> getOne(Long id) {
        return repo.findById(id);
    }

    public PolaganjePredispitneObaveze save(PolaganjePredispitneObaveze polaganjePredispitneObaveze) {
        return repo.save(polaganjePredispitneObaveze);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
