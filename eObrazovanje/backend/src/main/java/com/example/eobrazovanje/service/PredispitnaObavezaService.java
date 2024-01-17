package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.PredispitnaObaveza;
import com.example.eobrazovanje.repository.PredispitnaObavezaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredispitnaObavezaService {

    @Autowired
    PredispitnaObavezaRepository repo;

    public Page<PredispitnaObaveza> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<PredispitnaObaveza> getOne(Long id) {
        return repo.findById(id);
    }

    public PredispitnaObaveza save(PredispitnaObaveza predispitnaObaveza) {
        return repo.save(predispitnaObaveza);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<PredispitnaObaveza> getAllByPredmet(Long predmetId) {
        return getAllByPredmet(predmetId);
    }
}
