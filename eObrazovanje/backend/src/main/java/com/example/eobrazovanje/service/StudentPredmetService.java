package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Predmet;
import com.example.eobrazovanje.model.StudentPredmet;
import com.example.eobrazovanje.repository.StudentPredmetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentPredmetService {

    @Autowired
    StudentPredmetRepository repo;

    public Page<StudentPredmet> getAll(Pageable pageable, String search, Integer minBodova, Integer maxBodova, Long predmetId) {
        return repo.findAllByFilter(search, minBodova, maxBodova, predmetId, pageable);
    }

    public List<StudentPredmet> findAllByStudentUsername(String username) {
        return repo.findAllByStudentUsername(username);
    }

    public Optional<StudentPredmet> getOne(Long id) {
        return repo.findById(id);
    }

    public StudentPredmet save(StudentPredmet studentPredmet) {
        return repo.save(studentPredmet);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
