package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Student;
import com.example.eobrazovanje.model.Uplata;
import com.example.eobrazovanje.repository.StudentRepository;
import com.example.eobrazovanje.repository.UplataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UplataService {

    @Autowired
    UplataRepository repo;

    @Autowired
    StudentRepository studentRepository;

    public Page<Uplata> getAll(Pageable pageable, String search, String studentUsername) {
        if (studentUsername!=null && !studentUsername.isEmpty()) {
            return repo.findAllByStudentUsername(pageable, studentUsername);
        }
        return repo.findAllByStudent_ImeContainsOrStudent_PrezimeContains(search, search, pageable);
    }

    public Optional<Uplata> getOne(Long id) {
        return repo.findById(id);
    }

    @Transactional
    public Uplata save(Uplata uplata) throws Exception {
        Optional<Student> studentOptional = studentRepository.findById(uplata.getStudent().getId());
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            uplata = repo.save(uplata);
            student.setStanje(student.getStanje()+uplata.getIznos());
            studentRepository.save(student);
            return uplata;
        }
        throw new Exception("Student ne postoji");
    }

    public Uplata storniraj(Long id) throws Exception {
        Optional<Uplata> uplata = getOne(id);
        if (uplata.isPresent() || !uplata.get().getStornirano()) {
            Uplata stornoUplata = new Uplata();
            stornoUplata.setStudent(uplata.get().getStudent());
            stornoUplata.setDatum(uplata.get().getDatum());
            stornoUplata.setIznos(- uplata.get().getIznos());
            stornoUplata.setStornirano(true);
            uplata.get().setStornirano(true);
            repo.save(uplata.get());
            return save(stornoUplata);
        }
        throw new Exception("Uplata ne postoji");
    }
}
