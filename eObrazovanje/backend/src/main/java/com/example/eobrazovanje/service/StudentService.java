package com.example.eobrazovanje.service;

import com.example.eobrazovanje.model.Role;
import com.example.eobrazovanje.model.Student;
import com.example.eobrazovanje.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Student> getAll(Pageable pageable, String search) {
        return repo.findAllByUsernameContainsOrImeContainsOrPrezimeContainsOrJmbgContainsOrAdresaContains(search, search, search, search, search, pageable);
    }

    public Optional<Student> getOne(long id) {
        return repo.findById(id);
    }

    public Optional<Student> getOne(String username) {
        return repo.findByUsername(username);
    }

    public Student save(Student student, boolean kreiraSe) throws NumberFormatException {
        Long.parseLong(student.getJmbg());
        student.setRole(Role.STUDENT);
        if (kreiraSe) {
            student.setPassword(passwordEncoder.encode("admin"));
        }
        return repo.save(student);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public boolean existsByJmbg(String jmbg) {
        return repo.getByJmbg(jmbg).isPresent();
    }

    public boolean existsByBrIndeksa(String brIndeksa) {
        return repo.getByBrojIndeksa(brIndeksa).isPresent();
    }

}
