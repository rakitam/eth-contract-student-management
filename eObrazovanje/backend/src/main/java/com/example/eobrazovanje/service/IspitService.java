package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Ispit;
import com.example.eobrazovanje.repository.IspitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class IspitService {

    @Autowired
    IspitRepository repo;

    public Page<Ispit> getAll(Pageable pageable, String search, String studentUsername) {
        if (studentUsername!=null && !studentUsername.isEmpty()) {
            return repo.findAllByStudentUsername(pageable, studentUsername);
        }
        return repo.findAllByPredajePredmetNazivContainsOrPredajeNastavnikImeContainsOrPredajeNastavnikPrezimeContainsOrStudentImeContainsOrStudentPrezimeContains(pageable, search, search, search, search, search);
    }

    @Transactional
    public Optional<Ispit> getOne(Long id) {
        System.out.printf("Input id %s\n", id);
        return repo.findById(id);
    }

    public Ispit save(Ispit ispit) throws Exception {
        // Check if the student already has results for the exam
        List<Ispit> existingResults = repo.findAllByStudentAndPredaje(ispit.getStudent(),
                ispit.getPredaje());
        if (!existingResults.isEmpty()) {
            if (existingResults.stream().anyMatch(result -> result.getKonacno())) {
                System.out.println("Jesi uso");
                throw new Exception("Student je vec polagao ovaj ispit.");
            }
        }

        if (ispit.getId() == null || ispit.getId() == 0) {
            return repo.save(ispit);
        } else {
            Optional<Ispit> ispitIzBaze = getOne(ispit.getId());
            if (ispitIzBaze.isPresent() && !ispitIzBaze.get().getKonacno()) {
                return repo.save(ispit);
            }
        }
        throw new Exception("Serverska greska");
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
